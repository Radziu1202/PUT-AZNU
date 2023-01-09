package org.bp.deliveryserivce;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bp.types.CancelDeliveryRequest;
import org.bp.types.BakingRequest;
import org.bp.types.BakingRequestResponse;
import org.bp.types.DeliveryException;
import org.bp.types.DeliveryPreviewException;
import org.bp.types.DeliveryPreviewRequest;
import org.bp.types.DeliveryRequest;
import org.bp.types.DeliveryResponse;
import org.bp.types.ExceptionResponse;
import org.bp.types.OrderInfo;
import org.bp.types.OrderPreviewRequest;
import org.bp.state.ProcessingEvent;
import org.bp.state.ProcessingState;
import org.bp.state.StateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@org.springframework.web.bind.annotation.RestController
public class DeliveryService extends RouteBuilder {

	@org.springframework.beans.factory.annotation.Value("${bakery.kafka.server}")
	private String bakeryKafkaServer;
	@org.springframework.beans.factory.annotation.Value("${bakery.service.type}")
	private String bakeryServiceType;
	
	

	@org.springframework.beans.factory.annotation.Autowired
	StateService deliveryStateService;
	
	HashMap<String, DeliveryResponse> deliveries = new HashMap<>();

	@Operation(summary = "delivery preview", description = "operations for delivery service", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })
	@org.springframework.web.bind.annotation.PostMapping("/deliveryPreview")
	public DeliveryResponse deliveryPreview(
			@org.springframework.web.bind.annotation.RequestBody DeliveryPreviewRequest deliveryPreviewRequest) throws DeliveryPreviewException {
		if (!deliveries.containsKey(deliveryPreviewRequest.getOrderId())) {
			DeliveryPreviewException de = new DeliveryPreviewException("There is no order with id = " + deliveryPreviewRequest.getOrderId());
			
			throw de;
		}
		return deliveries.get(deliveryPreviewRequest.getOrderId());

	}


	@Operation(summary = "orderDelivery", description = "operations for delivery service", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })

	@org.springframework.web.bind.annotation.PostMapping("/orderDelivery")
	public DeliveryResponse orderDelivery(
			@org.springframework.web.bind.annotation.RequestBody DeliveryRequest deliveryRequest) {
		if (deliveryRequest != null && deliveryRequest.getDeliveryPlace() == null) {
			throw new DeliveryException("Delivery place incorrect");
		}
		DeliveryResponse deliveryResponse = new DeliveryResponse();
		deliveryResponse.setDeliveryPlace(deliveryRequest.getDeliveryPlace());
		deliveryResponse.setDeliveryId("D"+UUID.randomUUID().toString());
		deliveryResponse.setDeliveryDate(setDate());
		deliveryResponse.setOrderId(deliveryRequest.getOrderId());
		deliveries.put(deliveryResponse.getOrderId(), deliveryResponse);
		return deliveryResponse;
	}

	private Date setDate() {
		Date today = new Date();
		Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
		return tomorrow;
	}
	
	
	@Operation(summary = "cancel delivery", description = "operations for deliveryService", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })

	@org.springframework.web.bind.annotation.PostMapping("/cancelDelivery")
	public DeliveryResponse cancelDeliveryOrder(
			@org.springframework.web.bind.annotation.RequestBody CancelDeliveryRequest cancelDeliveryRequest) {
		if (cancelDeliveryRequest != null && cancelDeliveryRequest.getDeliveryId() == null) {
			throw new DeliveryPreviewException("Incorrect order ID");
		}
		
		if (!deliveries.containsKey(cancelDeliveryRequest.getDeliveryId()))
			throw new DeliveryPreviewException("There is no order with id = " + cancelDeliveryRequest.getDeliveryId());
		else {
			
		}
		DeliveryResponse dr = deliveries.get(cancelDeliveryRequest.getDeliveryId());

		deliveries.remove(cancelDeliveryRequest.getDeliveryId());
		return dr;
	}
	
	
	private void delivery() {
		
		from("kafka:DeliveryPreviewReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("deliveryOrderPreview")
		.log("fired deliveryOrderPreview").unmarshal()
		.json(JsonLibrary.Jackson, DeliveryPreviewRequest.class)
		.process((exchange) -> {
			DeliveryPreviewRequest opr = exchange.getMessage().getBody(DeliveryPreviewRequest.class);
			DeliveryResponse dr = new DeliveryResponse();
			dr = deliveryPreview(opr);
			exchange.getMessage().setBody(dr);
		})
		.marshal().json()
		.log("delivery preview created")
		.to("stream:out")
        .unmarshal().json(JsonLibrary.Jackson, DeliveryResponse.class);
        
	;
		
		from("kafka:CancelReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("cancelDelivery")
		.log("fired cancelDelivery").unmarshal()
		.json(JsonLibrary.Jackson, OrderPreviewRequest.class)
		.process((exchange) -> {
			OrderPreviewRequest opr = exchange.getMessage().getBody(OrderPreviewRequest.class);
			CancelDeliveryRequest  dpr = new CancelDeliveryRequest();
			dpr.setDeliveryId(opr.getOrderId());
			DeliveryResponse dr = new DeliveryResponse();
			dr = cancelDeliveryOrder(dpr);
			exchange.getMessage().setBody(dr);
		})
		.marshal().json()
		.to("stream:out")
        .log("order preview created")
	;
		
		
		from("kafka:BakingReqTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("orderDelivery")
		.log("fired orderDelivery").unmarshal()
		.json(JsonLibrary.Jackson, BakingRequest.class)
		.process((exchange) -> {
			
			String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
			ProcessingState previousState = deliveryStateService.sendEvent(bakingOrderId, ProcessingEvent.START);
			if (previousState != ProcessingState.CANCELLED) {
				BakingRequest br = exchange.getMessage().getBody(BakingRequest.class);
				DeliveryRequest dr = new DeliveryRequest();
				dr.setOrderId(bakingOrderId);
	
				dr.setDeliveryPlace(br.getDeliveryPlace());
				DeliveryResponse deliveryResponse = orderDelivery(dr);
				
				BakingRequestResponse brr = new BakingRequestResponse();
				brr.setOrderDeliveryResponse(deliveryResponse);
				exchange.getMessage().setBody(brr);
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
				.to("kafka:OrderInfoGateWayTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType)
			.endChoice()		
		;
		

	from("kafka:BakingOrderFailTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).routeId("deliveryOrderCompensation")
			.log("fired deliveryOrderCompensation").unmarshal()
			.json(JsonLibrary.Jackson, ExceptionResponse.class)
			.choice().when(header("serviceType").isNotEqualTo("delivery")).process((exchange) -> {
				String bakingOrderId = exchange.getMessage().getHeader("bakingOrderId", String.class);
				ProcessingState previousState = deliveryStateService.sendEvent(bakingOrderId, ProcessingEvent.CANCEL);
				CancelDeliveryRequest cdr = new CancelDeliveryRequest();
				cdr.setDeliveryId(bakingOrderId);
				exchange.getMessage().setBody(cdr);
				exchange.getMessage().setHeader("previousState", previousState);
			}).choice().when(header("previousState").isEqualTo(ProcessingState.FINISHED))
			.marshal().json()
			.to("stream:out")
			.to("direct:deliveryOrderCompensationAction").endChoice();

	from("direct:deliveryOrderCompensationAction").routeId("deliveryOrderCompensationAction")
			.log("fired deliveryOrderCompensationAction").unmarshal()
			.json(JsonLibrary.Jackson, CancelDeliveryRequest.class)
			.process((exchange) -> {
				cancelDeliveryOrder(exchange.getMessage().getBody(CancelDeliveryRequest.class));
			})
			.to("stream:out");
	}
	
	private void deliveryOrderExceptionHandlers() {
		onException(DeliveryException.class).process((exchange) -> {
			ExceptionResponse er = new ExceptionResponse();
			Date date = new Date(System.currentTimeMillis());
			er.setTimestamp(date);
			Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
			er.setMessage(cause.getMessage());
			exchange.getMessage().setBody(er);
		}).marshal().json().to("stream:out")
			.setHeader("serviceType", constant("delivery"))
				.to("kafka:BakingOrderFailTopic?brokers=" + bakeryKafkaServer + "&groupId=" + bakeryServiceType).handled(true)
				;	
		
		
		onException(DeliveryPreviewException.class).process((exchange) -> {
			ExceptionResponse er = new ExceptionResponse();
			Date date = new Date(System.currentTimeMillis());
			er.setTimestamp(date);
			Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
			er.setMessage(cause.getMessage());
			exchange.getMessage().setBody(er);
		}).marshal().json()
		.to("stream:out")
        .unmarshal().json(JsonLibrary.Jackson, ExceptionResponse.class);
		
	}

	@Override
	public void configure() throws Exception {
		deliveryOrderExceptionHandlers();
		delivery();
		
	}
}
