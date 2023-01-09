package org.bp.onlinebakery;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bp.CancelCakeOrderRequest;
import org.bp.OrderCakeRequest;
import org.bp.OrderPreviewRequest;
import org.bp.state.StateService;
import org.bp.state.ProcessingEvent;
import org.bp.state.ProcessingState;
import org.bp.types.BakingRequest;
import org.bp.types.BakingRequestResponse;
import org.bp.types.Cake;
import org.bp.types.DeliveryResponse;
import org.bp.types.ExceptionResponse;
import org.bp.types.OrderException;
import org.bp.types.OrderInfo;
import org.bp.types.OrderPreviewException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@org.springframework.stereotype.Service
public class OnlineBakeryEndpoint extends RouteBuilder implements OnlineBakery {
	private static double BASIC_CAKE_COST=10;
	HashMap<String, OrderInfo> cakes = new HashMap<>();
	 private static final Logger LOGGER = LogManager.getLogger(OnlineBakeryEndpoint.class);
	 
	 
	@org.springframework.beans.factory.annotation.Value("${bakery.kafka.server}")
	private String bakeryKafkaServer;
	@org.springframework.beans.factory.annotation.Value("${bakery.service.type}")
	private String bakeryServiceType;
	

	@org.springframework.beans.factory.annotation.Autowired
	StateService cakeStateService;
	@Override
	public OrderInfo cancelCakeOrder(CancelCakeOrderRequest payload) throws OrderPreviewExceptionMsg {
		if(!cakes.containsKey(payload.getOrderId()))
			throw new OrderPreviewExceptionMsg("There is no order with id = " + payload.getOrderId() );
		OrderInfo oi = cakes.get(payload.getOrderId());

		cakes.remove(payload.getOrderId());
		return oi;
	}

	@Override
	public OrderInfo orderCake(OrderCakeRequest payload) throws OrderExceptionMsg {
		
		if ( payload.getCake().getCakeType() == null ||
				 payload.getDeliveryPlace().getAddress() == null ||
				 payload.getPerson().getLastName() == null  || payload.getPerson().getFirstName().equals("Radek")
				 ) {
			OrderException oe = new OrderException();
			oe.setCode(323);
			oe.setError("Wrong order data. Correct your order");
			OrderExceptionMsg fault = new OrderExceptionMsg("Cannot complete order.", oe);
			throw fault;
		}
		OrderInfo oi = new OrderInfo();
		oi.setBirthdayName(payload.getCake().getBirthdayName());
		oi.setIsVegan(payload.getCake().isIsVegan());
		oi.setCakeType(payload.getCake().getCakeType());		
		oi.setPerson(payload.getPerson());
		oi.setDeliveryPlace(payload.getDeliveryPlace());
		oi.setPayment(payload.getPayment());
		
		oi.setId(payload.getOrderId());
		oi.setCost(determineCost(payload.getCake()));
		cakes.put(oi.getId(), oi);
		return oi;
		
	}

	
	
	
	private BigDecimal determineCost(Cake cake) {
		double cost = BASIC_CAKE_COST;
		if ( cake.getCakeType().equals("Chocolate")) {
			cost*=1.5;
		}else if (cake.getCakeType().equals("Vanilla")) {
			cost*=1.3;
		}else if (cake.getCakeType().equals("Fruit")) {
			cost*=2;
		}
		
		if ( cake.isIsVegan()){
			cost*=1.5;
		}
		return BigDecimal.valueOf( cost);
		
	}

	@Override
	public OrderInfo orderPreview(OrderPreviewRequest payload) throws OrderPreviewExceptionMsg {
		if(!cakes.containsKey(payload.getOrderId())) {
			OrderPreviewException oe = new OrderPreviewException();
			oe.setCode(321);
			oe.setError("There is no order with id = " + payload.getOrderId());
			OrderPreviewExceptionMsg fault = new OrderPreviewExceptionMsg("Cannot find order.", oe);
			throw fault;
		}
			
		return cakes.get(payload.getOrderId());
		
	}

	@Override
	public void payForOrder(String payload) throws OrderExceptionMsg {
		OrderInfo cakeInfo = cakes.get(payload);
		cakes.put(payload, cakeInfo);
	}
	
	private void cake() {
		
		from("kafka:PreviewReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("cakeOrderPreview")
		.log("fired cakeOrderPreview").unmarshal()
		.json(JsonLibrary.Jackson, OrderPreviewRequest.class)
		.process((exchange) -> {
			OrderPreviewRequest opr = exchange.getMessage().getBody(OrderPreviewRequest.class);
			OrderInfo oi = new OrderInfo();
			LOGGER.error("-------------------ORDERID------- " + opr.getOrderId());
			oi = orderPreview(opr);
			exchange.getMessage().setBody(oi);
		})
		.marshal().json()
		.log("oreder preview created")
		.to("stream:out")
        .unmarshal().json(JsonLibrary.Jackson, OrderInfo.class);

	;
		
		
		from("kafka:CancelReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("cancelOrder")
		.log("fired cancelOrder").unmarshal()
		.json(JsonLibrary.Jackson, OrderPreviewRequest.class)
		.process((exchange) -> {
			OrderPreviewRequest opr = exchange.getMessage().getBody(OrderPreviewRequest.class);
			CancelCakeOrderRequest  dpr = new CancelCakeOrderRequest();
			dpr.setOrderId(opr.getOrderId());
			OrderInfo dr = new OrderInfo();
			dr = cancelCakeOrder(dpr);
			exchange.getMessage().setBody(dr);
		})
		.marshal().json()
		.to("stream:out")
        .log("order preview created")
	;
		

		from("kafka:BakingReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("orderCake")
			.log("fired orderCake").unmarshal()
			.json(JsonLibrary.Jackson, BakingRequest.class)
			.process((exchange) -> {
				
				String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
				ProcessingState previousState = cakeStateService.sendEvent(bakingOrderId, ProcessingEvent.START);
				if (previousState != ProcessingState.CANCELLED) {
					BakingRequest br = exchange.getMessage().getBody(BakingRequest.class);
					BakingRequestResponse brr = new BakingRequestResponse();
					OrderCakeRequest ocr = Utils.prepareCakeOrderRequest(br);
					ocr.setOrderId(bakingOrderId);
					OrderInfo oi = orderCake(ocr);
					brr.setOrderCakeResponse(oi);
					
					exchange.getMessage().setBody(brr);
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
					.to("kafka:OrderInfoGateWayTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType)
				.endChoice()		
			;
			

		from("kafka:BakingOrderFailTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("cakeOrderCompensation")
				.log("fired cakeOrderCompensation").unmarshal()
				.json(JsonLibrary.Jackson, ExceptionResponse.class)
				.choice()
					.when(header("serviceType").isNotEqualTo("cake"))
					.process((exchange) -> {
					String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
					ProcessingState previousState = cakeStateService.sendEvent(bakingOrderId, ProcessingEvent.CANCEL);
					CancelCakeOrderRequest ccor = new CancelCakeOrderRequest();
					ccor.setOrderId(bakingOrderId);
					exchange.getMessage().setBody(ccor);
					exchange.getMessage().setHeader("previousState", previousState);
				})
				.choice()
				.when(header("previousState").isEqualTo(ProcessingState.FINISHED))
					.marshal().json()
					.to("stream:out")
					.to("direct:cakeOrderCompensationAction")
					.endChoice().endChoice();

		from("direct:cakeOrderCompensationAction").routeId("cakeOrderCompensationAction")
				.log("fired cakeOrderCompensationAction")
				.unmarshal()
				.json(JsonLibrary.Jackson, CancelCakeOrderRequest.class)
				.process((exchange) -> {
					cancelCakeOrder(exchange.getMessage().getBody(CancelCakeOrderRequest.class));
				})
				.to("stream:out");
		
		
	}
	
	private void cakeOrderExceptionHandlers() {
		onException(OrderExceptionMsg.class).process((exchange) -> {
			ExceptionResponse er = new ExceptionResponse();
			Date date = new Date(System.currentTimeMillis());
			er.setTimestamp(date);
			Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
			er.setMessage(cause.getMessage());
			exchange.getMessage().setBody(er);
		}).marshal().json().to("stream:out")
			.setHeader("serviceType", constant("cake"))
				.to("kafka:BakingOrderFailTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).handled(true)
				;	
		onException(OrderPreviewExceptionMsg.class).process((exchange) -> {
			ExceptionResponse er = new ExceptionResponse();
			Date date = new Date(System.currentTimeMillis());
			er.setTimestamp(date);
			Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
			er.setMessage(cause.getMessage());
			exchange.getMessage().setBody(er);
		}).marshal().json()
        .log("order preview created").to("stream:out")
        .unmarshal().json(JsonLibrary.Jackson, ExceptionResponse.class);
		
	}

	@Override
	public void configure() throws Exception {
		cakeOrderExceptionHandlers();
		cake();
	}


}
