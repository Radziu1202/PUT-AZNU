package org.bp.breadbakeryservice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bp.OrderPreviewRequest;
import org.bp.breadbakeryservice.model.Bread;
import org.bp.breadbakeryservice.model.BreadOrderException;
import org.bp.breadbakeryservice.model.CancelBreadOrderRequest;
import org.bp.breadbakeryservice.model.CancelBreadOrderRequestResponse;
import org.bp.breadbakeryservice.model.OrderBreadRequest;
import org.bp.breadbakeryservice.model.OrderBreadResponse;
import org.bp.onlinebakery.OrderExceptionMsg;
import org.bp.paymentbakery.ExceptionResponse;
import org.bp.paymentbakery.model.PaymentException;
import org.bp.paymentbakery.model.PaymentRequest;
import org.bp.paymentbakery.model.PaymentResponse;
import org.bp.types.OrderException;
import org.bp.types.OrderInfo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@org.springframework.web.bind.annotation.RestController
public class BreadBakeryService {

	HashMap<String, OrderBreadResponse> breadsPaid = new HashMap<>();
	HashMap<String, OrderBreadResponse> breadsNotPaid = new HashMap<>();
	@Operation(summary = "bread order preview", description = "operations for bread bakery", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OrderBreadResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })
	@org.springframework.web.bind.annotation.PostMapping("/orderPreview")
	public OrderBreadResponse orderPreview(
			@org.springframework.web.bind.annotation.RequestBody OrderPreviewRequest orderPreviewRequest) {
		if (!breadsPaid.containsKey(orderPreviewRequest.getOrderId())) {
			BreadOrderException boe = new BreadOrderException("There is no order with id = " + orderPreviewRequest.getOrderId());
			
			throw boe;
		}
		return breadsPaid.get(orderPreviewRequest.getOrderId());

	}

	private final double BASIC_BREAD_COST = 3;

	@Operation(summary = "bread ordering and canceling", description = "operations for bread bakery", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OrderBreadResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })

	@org.springframework.web.bind.annotation.PostMapping("/orderBread")
	public OrderBreadResponse orderBread(
			@org.springframework.web.bind.annotation.RequestBody OrderBreadRequest orderBreadRequest) {
		if (orderBreadRequest != null && orderBreadRequest.getDeliveryPlace() == null) {
			throw new BreadOrderException("Delivery place incorrect");
		} else if (orderBreadRequest != null && orderBreadRequest.getBread() == null) {
			throw new BreadOrderException("Bread Order incorrect");
		} else if (orderBreadRequest != null && orderBreadRequest.getPerson() == null) {
			throw new BreadOrderException("Person Data incorrect");
		}else if (orderBreadRequest != null && orderBreadRequest.getBread().getBreadType() == null) {
			throw new BreadOrderException("Bread order data incorrect");
		}
		OrderBreadResponse orderBreadResponse = new OrderBreadResponse();
		orderBreadResponse.setPerson(orderBreadRequest.getPerson());
		orderBreadResponse.setBread(orderBreadRequest.getBread());
		orderBreadResponse.setId("B"+UUID.randomUUID().toString());
		orderBreadResponse.setCost(setCost(orderBreadRequest.getBread()));
		breadsNotPaid.put(orderBreadResponse.getId(), orderBreadResponse);
		return orderBreadResponse;
	}

	private BigDecimal setCost(Bread bread) {
		double cost = BASIC_BREAD_COST;
		if (bread.getBreadType().equals("Ciabatta")) {
			cost *= 1.5;
		} else if (bread.getBreadType().equals("Focaccia")) {
			cost *= 1.3;
		} else if (bread.getBreadType().equals("Multigrain")) {
			cost *= 2;
		}

		if (bread.isIsGlutenFree()) {
			cost *= 1.5;
		}
		return BigDecimal.valueOf(cost);
	}
	
	
	@Operation(summary = "cancel bread order", description = "operations for bread bakery", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OrderBreadResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })

	@org.springframework.web.bind.annotation.PostMapping("/cancelBreadOrder")
	public CancelBreadOrderRequestResponse cancelBreadOrder(
			@org.springframework.web.bind.annotation.RequestBody CancelBreadOrderRequest cancelBreadOrderRequest) {
		if (cancelBreadOrderRequest != null && cancelBreadOrderRequest.getOrderId() == null) {
			throw new BreadOrderException("Incorrect order ID");
		}
		
		if (!breadsPaid.containsKey(cancelBreadOrderRequest.getOrderId()))
			throw new BreadOrderException("There is no order with id = " + cancelBreadOrderRequest.getOrderId());
		else {
			
		}
		CancelBreadOrderRequestResponse cbor = new CancelBreadOrderRequestResponse(breadsPaid.get(cancelBreadOrderRequest.getOrderId()));

		breadsPaid.remove(cancelBreadOrderRequest.getOrderId());
		return cbor;
	}

	@org.springframework.web.bind.annotation.PostMapping("/payForOrder")
	public void payForOrder(@org.springframework.web.bind.annotation.RequestBody String orderId) {
		OrderBreadResponse obr =  breadsNotPaid.get(orderId);
		breadsPaid.put(orderId, obr);

	}
	
	
	
}
