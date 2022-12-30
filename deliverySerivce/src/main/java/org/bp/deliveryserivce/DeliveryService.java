package org.bp.deliveryserivce;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bp.deliveryserivce.model.CancelDeliveryRequest;
import org.bp.deliveryserivce.model.DeliveryException;
import org.bp.deliveryserivce.model.DeliveryPreviewRequest;
import org.bp.deliveryserivce.model.DeliveryRequest;
import org.bp.deliveryserivce.model.DeliveryResponse;
import org.bp.deliveryserivce.model.ExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@org.springframework.web.bind.annotation.RestController
public class DeliveryService {

	HashMap<String, DeliveryResponse> deliveries = new HashMap<>();

	@Operation(summary = "delivery preview", description = "operations for delivery service", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })
	@org.springframework.web.bind.annotation.PostMapping("/deliveryPreview")
	public DeliveryResponse deliveryPreview(
			@org.springframework.web.bind.annotation.RequestBody DeliveryPreviewRequest deliveryPreviewRequest) {
		if (!deliveries.containsKey(deliveryPreviewRequest.getOrderId())) {
			DeliveryException de = new DeliveryException("There is no order with id = " + deliveryPreviewRequest.getOrderId());
			
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
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		return dt;
	}
	
	
	@Operation(summary = "cancel delivery", description = "operations for deliveryService", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })

	@org.springframework.web.bind.annotation.PostMapping("/cancelDelivery")
	public DeliveryResponse cancelBreadOrder(
			@org.springframework.web.bind.annotation.RequestBody CancelDeliveryRequest cancelDeliveryRequest) {
		if (cancelDeliveryRequest != null && cancelDeliveryRequest.getDeliveryId() == null) {
			throw new DeliveryException("Incorrect order ID");
		}
		
		if (!deliveries.containsKey(cancelDeliveryRequest.getDeliveryId()))
			throw new DeliveryException("There is no order with id = " + cancelDeliveryRequest.getDeliveryId());
		else {
			
		}
		DeliveryResponse dr = deliveries.get(cancelDeliveryRequest.getDeliveryId());

		deliveries.remove(cancelDeliveryRequest.getDeliveryId());
		return dr;
	}
}
