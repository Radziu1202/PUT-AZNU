package org.bp.onlinebakery;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import org.bp.CancelCakeOrderRequest;
import org.bp.OrderCakeRequest;
import org.bp.OrderPreviewRequest;
import org.bp.types.Cake;
import org.bp.types.OrderException;
import org.bp.types.OrderInfo;

@org.springframework.stereotype.Service
public class OnlineBakeryEndpoint implements OnlineBakery {
	private static double BASIC_CAKE_COST=10;
	HashMap<String, OrderInfo> cakesNotPaid = new HashMap<>();
	HashMap<String, OrderInfo> cakesPaid = new HashMap<>();

	@Override
	public OrderInfo cancelCakeOrder(CancelCakeOrderRequest payload) throws OrderExceptionMsg {
		if(!cakesPaid.containsKey(payload.getOrderId()))
			throw new OrderExceptionMsg("There is no order with id = " + payload.getOrderId() );
		return cakesPaid.get(payload.getOrderId());
	}

	@Override
	public OrderInfo orderCake(OrderCakeRequest payload) throws OrderExceptionMsg {
		
		if ( payload.getCake().getCakeType() == null ||
				 payload.getDeliveryPlace().getAddress() == null ||
				 payload.getPerson().getLastName() == null 
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
		
		oi.setId("C"+UUID.randomUUID().toString());
		oi.setCost(determineCost(payload.getCake()));
		cakesNotPaid.put(oi.getId(), oi);
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
	public OrderInfo orderPreview(OrderPreviewRequest payload) throws OrderExceptionMsg {
		if(!cakesPaid.containsKey(payload.getOrderId())) {
			OrderException oe = new OrderException();
			oe.setCode(321);
			oe.setError("There is no order with id = " + payload.getOrderId());
			OrderExceptionMsg fault = new OrderExceptionMsg("Cannot find order.", oe);
			throw fault;
		}
			
		return cakesPaid.get(payload.getOrderId());
		
	}

	@Override
	public void payForOrder(String payload) throws OrderExceptionMsg {
		OrderInfo cakeInfo = cakesNotPaid.get(payload);
		cakesPaid.put(payload, cakeInfo);
	}
	
	

}
