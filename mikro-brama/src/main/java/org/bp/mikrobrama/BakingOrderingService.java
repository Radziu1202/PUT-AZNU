package org.bp.mikrobrama;

import static org.apache.camel.model.rest.RestParamType.body;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bp.mikrobrama.model.BakingRequest;
import org.bp.mikrobrama.model.BakingRequestResponse;
import org.bp.mikrobrama.model.DeliveryResponse;
import org.bp.types.DeliveryPreviewRequest;
import org.bp.types.OrderInfo;
import org.bp.types.OrderPreviewRequest;
import org.bp.mikrobrama.model.Utils;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.bp.mikrobrama.PaymentService.PaymentData;
import org.bp.mikrobrama.exceptions.DeliveryException;
import org.bp.mikrobrama.exceptions.OrderExceptionMsg;
import org.bp.mikrobrama.model.ExceptionResponse;
import org.bp.mikrobrama.model.OrderBreadResponse;
import org.bp.mikrobrama.state.ProcessingEvent;
import org.bp.mikrobrama.state.ProcessingState;
import org.bp.mikrobrama.state.StateService;

@Component
public class BakingOrderingService extends RouteBuilder {
	 private static final Logger LOGGER = LogManager.getLogger(BakingOrderingService.class);
	 
	 private HashMap<String, PaymentData> orders=new HashMap<>();
		

	@org.springframework.beans.factory.annotation.Value("${bakery.kafka.server}")
	private String bakeryKafkaServer;
	@org.springframework.beans.factory.annotation.Value("${bakery.cake.server}")
	private String bakeryCakeServer;
	@org.springframework.beans.factory.annotation.Value("${bakery.service.type}")
	private String bakeryServiceType;
	
	@org.springframework.beans.factory.annotation.Autowired
	BakeryIdentifierService bakeryIdentifierService;

	@org.springframework.beans.factory.annotation.Autowired
	PaymentService paymentService;

	@org.springframework.beans.factory.annotation.Autowired
	StateService cakeStateService;
	
	@org.springframework.beans.factory.annotation.Autowired
	StateService breadStateService;
	
	@org.springframework.beans.factory.annotation.Autowired
	StateService deliveryStateService;
	@Override
	public void configure() throws Exception {
	
		if (bakeryServiceType.equals("all") || bakeryServiceType.equals("gateway"))
			gateway();
		if (bakeryServiceType.equals("all") || bakeryServiceType.equals("payment"))
			payment();
	}
	

	private void gateway() {
		restConfiguration().component("servlet")
			.bindingMode(RestBindingMode.json)
			.dataFormatProperty("prettyPrint", "true")
			.enableCORS(true).contextPath("/api")
				// turn on swagger api-doc
			.apiContextPath("/api-doc")
			.apiProperty("api.title", "Micro baking ordering API")
			.apiProperty("api.version", "1.0.0");

		rest("/order").description("Micro baking ordering REST service")
			.consumes("application/json")
			.produces("application/json")
			.post("/ordering").description("order baking")
			.type(BakingRequest.class)
			.outType(String.class)
			.param().name("body")
			.type(body)
			.description("order Baking")
			.endParam().responseMessage().code(200)
			.message("Travel successfully booked").endResponseMessage()
			.to("direct:orderBaking");
		
		rest("/order").description("Micro baking ordering REST service")
		.consumes("application/json")
		.produces("application/json")
		.post("/orderPreview").description("order preview ")
		.type(OrderPreviewRequest.class)
		.outType(BakingRequestResponse.class)
		.param().name("body")
		.type(body)
		.description("preview order")
		.endParam().responseMessage().code(200)
		.message("Travel successfully booked").endResponseMessage()
		.to("direct:orderPreview");
		
		
		rest("/order").description("Micro baking ordering REST service")
		.consumes("application/json")
		.produces("application/json")
		.post("/deliveryPreview").description("order preview ")
		.type(DeliveryPreviewRequest.class)
		.outType(DeliveryResponse.class)
		.param().name("body")
		.type(body)
		.description("preview delivery")
		.endParam().responseMessage().code(200)
		.message("Travel successfully booked").endResponseMessage()
		.to("direct:deliveryPreview");
		
		rest("/order").description("Micro baking ordering REST service")
		.consumes("application/json")
		.produces("application/json")
		.post("/cancelOrder").description("cancel order baking")
		.type(OrderPreviewRequest.class)
		.outType(BakingRequestResponse.class)
		.param().name("body")
		.type(body)
		.description("cancel order")
		.endParam().responseMessage().code(200)
		.message("order Cancelled").endResponseMessage()
		.to("direct:cancelOrderBaking");

		
		from("direct:orderBaking").routeId("orderBaking")
		.streamCaching()
			.log("orderBaking fired")
			.process((exchange) -> {
				exchange.getMessage().setHeader("bakingOrderId", bakeryIdentifierService.getBakingIdentifier());
		})
			.to("direct:BakingRequest")
			.to("direct:BakingRequestResponse");
		
		
		
		from("direct:orderPreview").routeId("orderPreview")
		.streamCaching()
		.log("orderPreview fired")
		.to("direct:orderPreviewRequest")
		.to("direct:orderRequester");
	
		
		from("direct:deliveryPreview").routeId("deliveryPreview")
		.streamCaching()
		.log("deliveryPreview fired")
		.to("direct:deliveryPreviewRequest")
		;
		
		
	       
		from("direct:cancelOrderBaking").routeId("cancelOrderBaking")
		.streamCaching()
		.log("cancelOrderBaking fired")
		.to("direct:CancelBakingRequest")
		.to("direct:orderRequester2");

		from("direct:orderRequester").routeId("orderRequester")
		.log("orderRequester fired")
		.process((exchange) -> {
			String opr = exchange.getMessage().getBody(String.class);
			JsonObject jsonObject = new JsonParser().parse(opr).getAsJsonObject();
			PaymentData pd = getPaymentData(jsonObject.get("orderId").toString().replace("\"", ""));
			if (pd!=null) {
				exchange.getMessage().setBody(
						pd.bakingRequestResponse );	
			}
			
		});
		
		from("direct:orderRequester2").routeId("orderRequester2")
		.log("orderRequester2 fired")
		.process((exchange) -> {
			
			String opr = exchange.getMessage().getBody(String.class);
			JsonObject jsonObject = new JsonParser().parse(opr).getAsJsonObject();
			String id = jsonObject.get("orderId").toString().replace("\"", "");
			
			
			LOGGER.error("-------------------OPR!!!!!!!!------- " + id);
			PaymentData pd = getPaymentData(id);
			orders.remove(id);
			if (pd!=null) {
				exchange.getMessage().setBody(
						pd.bakingRequestResponse );	
			}
			
		});
		
		from("direct:BakingRequestResponse").routeId("BakingRequestResponse")
		.log("BakingRequestResponse fired")
		.process(
			(exchange) -> {
				String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
				exchange.getMessage().setBody(bakingOrderId);
								}
				)
		;   


		from("direct:orderRemover").routeId("orderRemover")
		.log("orderRemover fired")
		.process((exchange) -> {
			String opr = exchange.getMessage().getBody(String.class);
			JsonObject jsonObject = new JsonParser().parse(opr).getAsJsonObject();
			String id = jsonObject.get("orderId").toString().replace("\"", "");
			orders.remove(id);
			exchange.getMessage().setBody(opr);	
		});
		
			

		from("direct:BakingRequest").routeId("BakingRequest")
		.log("brokerTopic fired")
		.marshal().json()
				.to("kafka:BakingReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType);
				

		
		from("direct:orderPreviewRequest").routeId("orderPreviewRequest")
		.log("brokerTopicPreview fired")
		.marshal().json()
				.to("kafka:PreviewReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType);

		
		from("direct:deliveryPreviewRequest").routeId("deliveryPreviewRequest")
		.log("brokerTopicPreviewDelivery fired")
		.marshal().json()
				.to("kafka:DeliveryPreviewReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType);


		from("direct:CancelBakingRequest").routeId("CancelBakingRequest")
		.log("CancelBakingRequest fired")
		.marshal().json()
				.to("kafka:CancelReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType);

		
		
		from("kafka:OrderInfoGateWayTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("paymentInfo")
		.log("fired dodanie order")
		.unmarshal().json(JsonLibrary.Jackson, BakingRequestResponse.class)
		.process((exchange) -> {
				String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
				String servicetype = exchange.getMessage().getHeader("serviceType", String.class);
				BakingRequestResponse response = exchange.getMessage().getBody(BakingRequestResponse.class);

				boolean isReady = addOrderInfo(bakingOrderId,
						response,
						servicetype);
				LOGGER.error("-------------------dporzmiar po dodaniu------- " + orders.size());
				exchange.getMessage().setHeader("isReady", isReady);
			});
		
	}

	private void payment() {

		from("kafka:OrderInfoTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("paymentInfo")
			.log("fired paymentInfo")
			.unmarshal().json(JsonLibrary.Jackson, BakingRequestResponse.class)
			.process((exchange) -> {
					String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
					String servicetype = exchange.getMessage().getHeader("serviceType", String.class);
					BakingRequestResponse response = exchange.getMessage().getBody(BakingRequestResponse.class);
					LOGGER.error("-------------------response------- " + response);
					LOGGER.error("-------------------cake------- " + response.getOrderCakeResponse());
					LOGGER.error("-------------------delivery------- " + response.getOrderDeliveryResponse());

					boolean isReady = paymentService.addOrderInfo(bakingOrderId,
							response,
							servicetype);
					exchange.getMessage().setHeader("isReady", isReady);
				})
			.choice()
			.when(header("isReady").isEqualTo(true))
				.to("direct:finalizePayment")
			.endChoice();

		from("kafka:BakingReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("paymentBakingReq").log("fired paymentBakingReq")
				.unmarshal().json(JsonLibrary.Jackson, BakingRequest.class).process((exchange) -> {
					String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
					LOGGER.error("-------------------size 3------- " + bakingOrderId);
					boolean isReady = paymentService.addBakingRequest(bakingOrderId,
							exchange.getMessage().getBody(BakingRequest.class));
					exchange.getMessage().setHeader("isReady", isReady);
				}).choice().when(header("isReady").isEqualTo(true)).to("direct:finalizePayment").endChoice();

		from("direct:finalizePayment").routeId("finalizePayment").log("fired finalizePayment").process((exchange) -> {
			String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
			PaymentService.PaymentData paymentData = paymentService.getPaymentData(bakingOrderId);
			
			BigDecimal cakeCost = paymentData.bakingRequestResponse.getOrderCakeResponse().getCost();
			BakingRequestResponse bakingRequestResponse = new BakingRequestResponse();
			bakingRequestResponse.setId(bakingOrderId);
	
			bakingRequestResponse.setOrderCakeResponse(paymentData.bakingRequestResponse.getOrderCakeResponse());
			bakingRequestResponse.setOrderDeliveryResponse(paymentData.bakingRequestResponse.getOrderDeliveryResponse());

			exchange.getMessage().setBody(bakingRequestResponse);

		})
		.marshal().json();
		;	
		
		from("direct:notification").routeId("notification")
		.log("fired notification").removeHeaders("CamelHttp*").
		to("stream:out");
		
		from("kafka:getRecordReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType)
		.routeId("getRecord")
		.log("fired getRecord").unmarshal().json().process((exchange) ->
		{
			String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
			PaymentService.PaymentData pd = paymentService.getPaymentData(bakingOrderId);
			LOGGER.error("-------------------pdpd------- " + pd.bakingRequestResponse.getOrderDeliveryResponse());
			exchange.getMessage().setBody(pd);
			
		})
;
	}
	
	
	public synchronized boolean addOrderInfo(String bakingOrderId, BakingRequestResponse bakingRequestResponse, String serviceType) {
		PaymentData paymentData = getPaymentData(bakingOrderId);
		LOGGER.error("-------------------pobranie paymentdata:  ------- ");

		if (serviceType.equals("cake"))
			paymentData.bakingRequestResponse.setOrderCakeResponse(bakingRequestResponse.getOrderCakeResponse()); ;
		if (serviceType.equals("bread"))
			paymentData.bakingRequestResponse.setOrderBreadResponse(bakingRequestResponse.getOrderBreadResponse()); ;
		if (serviceType.equals("delivery"))
			paymentData.bakingRequestResponse.setOrderDeliveryResponse(bakingRequestResponse.getOrderDeliveryResponse()); ;
		
		LOGGER.error("-------------------paymentData po dodaniu 2 ------- " + paymentData.bakingRequestResponse);

		return paymentData.isReady();
	}

	public synchronized PaymentData getPaymentData(String bakingOrderId) {
		LOGGER.error("-------------------orders ------- " + orders);
		LOGGER.error("-------------------bakingOrderId ------- " + bakingOrderId);
		if (orders.size()>0) {
			
				LOGGER.error("-------------------keyset:  ------- "+  orders.keySet() );
				
		}

		LOGGER.error("-------------------rozmiar ------- " + orders.size() );
		
		
		PaymentData paymentData = orders.get(bakingOrderId);
		if (paymentData == null) {
			paymentData = new PaymentData();
			paymentData.bakingRequestResponse= new BakingRequestResponse();
			orders.put(bakingOrderId, paymentData);
		}
		return paymentData;
	}
	
	
	private void bread() {
		from("kafka:BakingReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("orderBread")
			.log("fired orderBread").unmarshal()
			.json(JsonLibrary.Jackson, BakingRequest.class)
			.process((exchange) -> {

				String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
				ProcessingState previousState = breadStateService.sendEvent(bakingOrderId, ProcessingEvent.START);
				if (previousState != ProcessingState.CANCELLED) {
					BakingRequestResponse bakingRequestResponse = new BakingRequestResponse();
					bakingRequestResponse.setOrderBreadResponse(new OrderBreadResponse());
					bakingRequestResponse.setId(bakeryIdentifierService.getBakingIdentifier());
					BakingRequest br = exchange.getMessage().getBody(BakingRequest.class);
						if (br != null && br.getBread() != null && br.getBread().getBreadType() != null) {
							String type = br.getBread().getBreadType();
							if (type.equals("Ciabatta")) {
								bakingRequestResponse.getOrderBreadResponse().setCost(new BigDecimal(20));
							} else {
								bakingRequestResponse.getOrderBreadResponse().setCost(new BigDecimal(10));
							}
							if (br.getBread().getGlutenFree()) {
								bakingRequestResponse.getOrderBreadResponse().setCost( bakingRequestResponse.getOrderBreadResponse().getCost().multiply(new BigDecimal(1.5)));
							}
						}
						exchange.getMessage().setBody(bakingRequestResponse);
						previousState = breadStateService.sendEvent(bakingOrderId, ProcessingEvent.FINISH);
					}
					exchange.getMessage().setHeader("previousState", previousState);
					
				}).marshal().json()
				.to("stream:out")
				.choice()
					.when(header("previousState").isEqualTo(ProcessingState.CANCELLED))
					.to("direct:cakeOrderCompensationAction")
				.otherwise()
				.setHeader("serviceType", constant("bread"))
				.to("kafka:OrderInfoTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType)
				.endChoice()		
			;
			

		from("kafka:BakingOrderFailTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("breadOrderCompensation")
				.log("fired breadOrderCompensation").unmarshal().json(JsonLibrary.Jackson, ExceptionResponse.class)
				.choice().when(header("serviceType").isNotEqualTo("bread")).process((exchange) -> {
					String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
					ProcessingState previousState = breadStateService.sendEvent(bakingOrderId, ProcessingEvent.CANCEL);
					exchange.getMessage().setHeader("previousState", previousState);
				}).choice().when(header("previousState").isEqualTo(ProcessingState.FINISHED))
				.to("direct:breadOrderCompensationAction").endChoice().endChoice();

		from("direct:breadOrderCompensationAction").routeId("breadOrderCompensationAction")
				.log("fired breadOrderCompensationAction").to("stream:out");

	}

}
