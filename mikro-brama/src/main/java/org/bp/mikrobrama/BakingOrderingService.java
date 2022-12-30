package org.bp.mikrobrama;

import static org.apache.camel.model.rest.RestParamType.body;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.apache.camel.model.rest.RestParamType.body;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.bp.mikrobrama.model.BakingRequest;
import org.bp.mikrobrama.model.BakingRequestResponse;
import org.bp.mikrobrama.model.DeliveryResponse;
import org.bp.mikrobrama.model.OrderInfo;
import org.bp.mikrobrama.model.Utils;
import org.springframework.stereotype.Component;
import org.bp.mikrobrama.exceptions.DeliveryException;
import org.bp.mikrobrama.exceptions.OrderException;
import org.bp.mikrobrama.model.ExceptionResponse;
import org.bp.mikrobrama.model.OrderBreadResponse;
import org.bp.mikrobrama.state.ProcessingEvent;
import org.bp.mikrobrama.state.ProcessingState;
import org.bp.mikrobrama.state.StateService;

@Component
public class BakingOrderingService extends RouteBuilder {

	@org.springframework.beans.factory.annotation.Value("${bakery.kafka.server}")
	private String bakeryKafkaServer;
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
		if (bakeryServiceType.equals("all") || bakeryServiceType.equals("cake"))
			cakeOrderExceptionHandlers();
		if (bakeryServiceType.equals("all") || bakeryServiceType.equals("delivery"))
			deliveryOrderExceptionHandlers();
		if (bakeryServiceType.equals("all") || bakeryServiceType.equals("gateway"))
			gateway();
		if (bakeryServiceType.equals("all") || bakeryServiceType.equals("payment"))
			payment();
		if (bakeryServiceType.equals("all") || bakeryServiceType.equals("delivery"))
			delivery();
		if (bakeryServiceType.equals("all") || bakeryServiceType.equals("cake"))
			cake();
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
			.outType(BakingRequestResponse.class)
			.param().name("body")
			.type(body)
			.description("The travel to book")
			.endParam().responseMessage().code(200)
			.message("Travel successfully booked").endResponseMessage()
			.to("direct:orderBaking");

		from("direct:orderBaking").routeId("orderBaking")
			.log("orderBaking fired")
			.process((exchange) -> {
				exchange.getMessage().setHeader("bakingOrderId", bakeryIdentifierService.getBakingIdentifier());
		})
			.to("direct:BakingRequest")
			.to("direct:orderRequester");

		from("direct:orderRequester").routeId("orderRequester")
		.log("orderRequester fired")
		.process((exchange) -> {
			exchange.getMessage().setBody(
					Utils.prepareBakingRequestResponse(exchange.getMessage().getHeader("bakingOrderId", String.class), null));
		});

		from("direct:BakingRequest").routeId("BakingRequest")
		.log("brokerTopic fired")
		.marshal().json()
				.to("kafka:BakingReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType);

	}

	private void cake() {
		from("kafka:BakingReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("orderCake")
			.log("fired orderCake").unmarshal()
			.json(JsonLibrary.Jackson, BakingRequest.class)
			.process((exchange) -> {

				String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
				ProcessingState previousState = cakeStateService.sendEvent(bakingOrderId, ProcessingEvent.START);
				if (previousState != ProcessingState.CANCELLED) {
					BakingRequestResponse bakingRequestResponse = new BakingRequestResponse();
					bakingRequestResponse.setOrderBreadResponse(new OrderBreadResponse());
					bakingRequestResponse.setOrderCakeResponse(new OrderInfo());
					bakingRequestResponse.setId(bakeryIdentifierService.getBakingIdentifier());
					BakingRequest br = exchange.getMessage().getBody(BakingRequest.class);
						if (br != null && br.getCake() != null && br.getCake().getCakeType() != null) {
							String type = br.getCake().getCakeType();
							if (type.equals("VANILLA")) {
								bakingRequestResponse.getOrderCakeResponse().setCost(new BigDecimal(20));
							} else {
								bakingRequestResponse.getOrderCakeResponse().setCost(new BigDecimal(10));
							}
							if (br.getCake().getBirthdayName().equals("Radek")) {
								throw new OrderException("Nie obsługiwane imie " + br.getCake().getBirthdayName());
							}
						}
						exchange.getMessage().setBody(bakingRequestResponse);
						previousState = cakeStateService.sendEvent(bakingOrderId, ProcessingEvent.FINISH);
					}
					exchange.getMessage().setHeader("previousState", previousState);
					
				}).marshal().json()
				.to("stream:out")
				.choice()
					.when(header("previousState").isEqualTo(ProcessingState.CANCELLED))
					.to("direct:cakeOrderCompensationAction")
				.otherwise()
					.setHeader("serviceType", constant("cake"))
					.to("kafka:OrderInfoTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType)
				.endChoice()		
			;
			

		from("kafka:BakingOrderFailTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("cakeOrderCompensation")
				.log("fired cakeOrderCompensation").unmarshal()
				.json(JsonLibrary.Jackson, ExceptionResponse.class)
				.choice().when(header("serviceType").isNotEqualTo("cake")).process((exchange) -> {
					String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
					ProcessingState previousState = cakeStateService.sendEvent(bakingOrderId, ProcessingEvent.CANCEL);
					exchange.getMessage().setHeader("previousState", previousState);
				}).choice().when(header("previousState").isEqualTo(ProcessingState.FINISHED))
				.to("direct:cakeOrderCompensationAction").endChoice().endChoice();

		from("direct:cakeOrderCompensationAction").routeId("cakeOrderCompensationAction")
				.log("fired cakeOrderCompensationAction").to("stream:out");

	}

	private void payment() {

		from("kafka:OrderInfoTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("paymentInfo")
			.log("fired paymentInfo")
			.unmarshal().json(JsonLibrary.Jackson, BakingRequestResponse.class)
			.process((exchange) -> {
					String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
					boolean isReady = paymentService.addOrderInfo(bakingOrderId,
							exchange.getMessage().getBody(BakingRequestResponse.class),
							exchange.getMessage().getHeader("serviceType", String.class));
					exchange.getMessage().setHeader("isReady", isReady);
				})
			.choice()
			.when(header("isReady").isEqualTo(true))
				.to("direct:finalizePayment")
			.endChoice();

		from("kafka:BakingReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("paymentBakingReq").log("fired paymentBakingReq")
				.unmarshal().json(JsonLibrary.Jackson, BakingRequest.class).process((exchange) -> {
					String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
					boolean isReady = paymentService.addBakingRequest(bakingOrderId,
							exchange.getMessage().getBody(BakingRequest.class));
					exchange.getMessage().setHeader("isReady", isReady);
				}).choice().when(header("isReady").isEqualTo(true)).to("direct:finalizePayment").endChoice();

		from("direct:finalizePayment").routeId("finalizePayment").log("fired finalizePayment").process((exchange) -> {
			String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
			PaymentService.PaymentData paymentData = paymentService.getPaymentData(bakingOrderId);
			BigDecimal cakeCost = paymentData.cakeOrderInfo.getOrderCakeResponse().getCost();
			BakingRequestResponse bakingRequestResponse = new BakingRequestResponse();
			bakingRequestResponse.setId(bakingOrderId);
			OrderInfo oi = new OrderInfo();
			oi.setCost(cakeCost);
			oi.setId(bakingOrderId);
			bakingRequestResponse.setOrderCakeResponse(oi);
			exchange.getMessage().setBody(bakingRequestResponse);
		}).to("direct:notification");

		from("direct:notification").routeId("notification").log("fired notification").to("stream:out");
	}

	private void cakeOrderExceptionHandlers() {
		onException(OrderException.class).process((exchange) -> {
			ExceptionResponse er = new ExceptionResponse();
			er.setTimestamp(OffsetDateTime.now());
			Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
			er.setMessage(cause.getMessage());
			exchange.getMessage().setBody(er);
		}).marshal().json().to("stream:out")
			.setHeader("serviceType", constant("cake"))
				.to("kafka:BakingOrderFailTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).handled(true)
				;	
	}
	
	private void delivery() {
		from("kafka:BakingReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("orderDelivery")
		.log("fired orderDelivery").unmarshal()
		.json(JsonLibrary.Jackson, BakingRequest.class)
		.process((exchange) -> {

			String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
			ProcessingState previousState = deliveryStateService.sendEvent(bakingOrderId, ProcessingEvent.START);
			if (previousState != ProcessingState.CANCELLED) {
				BakingRequestResponse bakingRequestResponse = new BakingRequestResponse();
				bakingRequestResponse.setId(bakeryIdentifierService.getBakingIdentifier());
				bakingRequestResponse.setOrderDeliveryResponse(new DeliveryResponse());
				
				BakingRequest br = exchange.getMessage().getBody(BakingRequest.class);
					if (br != null && br.getDeliveryPlace()  != null && br.getDeliveryPlace().getCity() != null) {
						String city = br.getDeliveryPlace().getCity();
						if (city.equals("Poznan")) {
							throw new DeliveryException("Nie obsługiwane miasto " + br.getDeliveryPlace().getCity());
						} 
					}
					exchange.getMessage().setBody(bakingRequestResponse);
					previousState = deliveryStateService.sendEvent(bakingOrderId, ProcessingEvent.FINISH);
				}
				exchange.getMessage().setHeader("previousState", previousState);
				
			}).marshal().json()
			.to("stream:out")
			.choice()
				.when(header("previousState").isEqualTo(ProcessingState.CANCELLED))
				.to("direct:deliveryOrderCompensationAction")
			.otherwise()
				.setHeader("serviceType", constant("delivery"))
				.to("kafka:OrderInfoTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType)
			.endChoice()		
		;
		

	from("kafka:BakingOrderFailTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("deliveryOrderCompensation")
			.log("fired deliveryOrderCompensation").unmarshal()
			.json(JsonLibrary.Jackson, ExceptionResponse.class)
			.choice().when(header("serviceType").isNotEqualTo("delivery")).process((exchange) -> {
				String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
				ProcessingState previousState = deliveryStateService.sendEvent(bakingOrderId, ProcessingEvent.CANCEL);
				exchange.getMessage().setHeader("previousState", previousState);
			}).choice().when(header("previousState").isEqualTo(ProcessingState.FINISHED))
			.to("direct:deliveryOrderCompensationAction").endChoice();

	from("direct:deliveryOrderCompensationAction").routeId("deliveryOrderCompensationAction")
			.log("fired deliveryOrderCompensationAction").to("stream:out");
	}
	
	
	
	private void deliveryOrderExceptionHandlers() {
		onException(DeliveryException.class).process((exchange) -> {
			ExceptionResponse er = new ExceptionResponse();
			er.setTimestamp(OffsetDateTime.now());
			Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
			er.setMessage(cause.getMessage());
			exchange.getMessage().setBody(er);
		}).marshal().json().to("stream:out")
			.setHeader("serviceType", constant("delivery"))
				.to("kafka:BakingOrderFailTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).handled(true)
				;	
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
