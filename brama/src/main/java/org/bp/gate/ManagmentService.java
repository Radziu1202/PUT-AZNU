package org.bp.gate;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.bp.bread.OrderBreadResponse;
import org.bp.CancelCakeOrderRequest;
import org.bp.OrderInfo;
import org.bp.gate.model.BakingRequest;
import org.bp.gate.model.BakingRequestResponse;
import org.bp.gate.model.GateException;
import org.bp.gate.model.Utils;
import org.bp.payment.PaymentRequest;
import org.bp.payment.PaymentResponse;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

import java.math.BigDecimal;

@Component
public class ManagmentService extends RouteBuilder {

    @org.springframework.beans.factory.annotation.Autowired
    BakeryIdentifierService bakeryIdentifierService;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true).contextPath("/api")
                // turn on swagger api-doc
                .apiContextPath("/api-doc").apiProperty("api.title", "Online Bakery API")
                .apiProperty("api.version", "2.0.0");

        rest("/orderBaking").description("Order Baking").consumes("application/json").produces("application/json")

                .post("/order").description("online bakery").type(BakingRequest.class)
                .outType(BakingRequestResponse.class).param().name("body").type(body).description("Baking order param")
                .endParam().responseMessage().code(200).message("The Baking order ordered successfully")
                .endResponseMessage().responseMessage().code(400).responseModel(GateException.class)
                .message("Post order exception").endResponseMessage().to("direct:orderBaking");

        cakes();
        breads();
        payments();

        from("direct:orderBaking").routeId("orderBaking").log("orderBaking fired")
                .process((exchange) -> {
                    exchange.setProperty("paymentRequest",
                            Utils.preparePaymentRequest(exchange.getMessage().getBody(BakingRequest.class)));
                    exchange.setProperty("bakeryOrderId", bakeryIdentifierService.generateOrderId());
                })
                .saga()
                .multicast()
                .parallelProcessing()
                .aggregationStrategy((prevEx, currentEx) -> {
                    if (currentEx.getException() != null)
                        return currentEx;
                    if (prevEx != null && prevEx.getException() != null)
                        return prevEx;
                    PaymentRequest paymentRequest;
                    if (prevEx == null)
                        paymentRequest = currentEx.getProperty("paymentRequest", PaymentRequest.class);
                    else
                        paymentRequest = prevEx.getMessage().getBody(PaymentRequest.class);

                    Object body = currentEx.getMessage().getBody();
                    BigDecimal cost;
                    String orderId;
                    if (body instanceof OrderInfo) {
                        cost = ((OrderInfo) body).getCost();
                        orderId = ((OrderInfo) body).getId();
                    } else if (body instanceof OrderBreadResponse) {
                        cost = ((OrderBreadResponse) body).getCost();
                        orderId = ((OrderBreadResponse) body).getId();
                    } else
                        return prevEx;
                    paymentRequest.setAmount(cost);
                    paymentRequest.setOrderId(orderId);
                    currentEx.getMessage().setBody(paymentRequest);
                    return currentEx;
                }).to("direct:orderCake").end().process(
                        (currentEx) -> {
                            currentEx.getMessage().setBody(currentEx.getProperty("paymentRequest",
                                    PaymentRequest.class));
                        }
                )
                .to("direct:payment").removeHeaders("Camel*")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
        ;

    }

    public void cakes() {

        final JaxbDataFormat jaxbCake = new JaxbDataFormat(OrderInfo.class.getPackage().getName());

        from("direct:cancelCakeOrder").routeId("cancelCakeOrder")
                .log("cancelCakeOrder fired")
                .process((exchange) -> {
                    String bakeryOrderId = exchange.getMessage().getHeader("bakeryOrderId", String.class);
                    String cakeOrderId = bakeryIdentifierService.getCakeOrderId(bakeryOrderId);
                    CancelCakeOrderRequest cancelCakeOrderRequest = new CancelCakeOrderRequest();
                    cancelCakeOrderRequest.setOrderId(cakeOrderId);
                    exchange.getMessage().setBody(cancelCakeOrderRequest);
                })
                .marshal(jaxbCake)
                .to("spring-ws:http://localhost:8080/soap-api/service/onlineBakery")
                .to("stream:out")
                .unmarshal(jaxbCake)
        ;

        from("direct:orderCake").routeId("orderCake")
                .log("orderCakeRequest fired")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .compensation("direct:cancelCakeOrder").option("bakeryOrderId",
                simple("${exchangeProperty.bakeryOrderId}"))
                .process((exchange) -> {
                    exchange.getMessage()
                            .setBody(Utils.prepareCakeOrderRequest(exchange.getMessage().getBody(BakingRequest.class)));
                }).marshal(jaxbCake).to("spring-ws:http://localhost:8080/soap-api/service/onlineBakery").to("stream:out")
                .unmarshal(jaxbCake)
                .process((exchange) -> {
                    OrderInfo cakeOrderResponse = exchange.getMessage().getBody(OrderInfo.class);
                    String bakeryOrderId = exchange.getProperty("bakeryOrderId", String.class);
                    bakeryIdentifierService.assignCakeOrderId(bakeryOrderId, cakeOrderResponse.getId());
                });


    }

    public void breads() {
        from("direct:orderBread").routeId("orderBread").log("orderBread fired").marshal().json()
                .removeHeaders("CamelHttp*").to("rest:post:orderBread?host=localhost:8085").to("stream:out").unmarshal()
                .json();

    }

    public void payments() {

        from("direct:payment").routeId("payment")
                .log("payment fired")
                .marshal().json()
                .removeHeaders("CamelHttp*")
                .to("rest:post:payment?host=localhost:8083")
                .unmarshal().json(JsonLibrary.Jackson, PaymentResponse.class)
                .to("stream:out")
                
        ;
    }
}
