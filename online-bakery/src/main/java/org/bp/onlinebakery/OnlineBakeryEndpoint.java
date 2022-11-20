package org.bp.onlinebakery;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import org.bp.CancelBreadOrderRequest;
import org.bp.CancelCakeOrderRequest;
import org.bp.OrderBreadRequest;
import org.bp.OrderCakeRequest;
import org.bp.OrderPreviewRequest;
import org.bp.types.Cake;
import org.bp.types.OrderInfo;

@org.springframework.stereotype.Service
public class OnlineBakeryEndpoint implements OnlineBakery {
	private static double BASIC_CAKE_COST=10;
	HashMap<String, OrderInfo> cakes = new HashMap<>();
	
	@Override
	public OrderInfo cancelCakeOrder(CancelCakeOrderRequest payload) throws OrderExceptionMsg {
		if(!cakes.containsKey(payload.getOrderId()))
			throw new OrderExceptionMsg("There is no order with id = " + payload.getOrderId() );
		return cakes.get(payload.getOrderId());
	}

	@Override
	public OrderInfo orderCake(OrderCakeRequest payload) throws OrderExceptionMsg {
		OrderInfo oi = new OrderInfo();
		oi.setBirthdayName(payload.getCake().getBirthdayName());
		oi.setIsVegan(payload.getCake().isIsVegan());
		oi.setCakeType(payload.getCake().getCakeType());		
		oi.setPerson(payload.getPerson());
		oi.setDeliveryPlace(payload.getDeliveryPlace());
		oi.setPayment(payload.getPayment());
		
		oi.setId(UUID.randomUUID().toString());
		oi.setCost(determineCost(payload.getCake()));
		cakes.put(oi.getId(), oi);
		return oi;
		
	}

	@Override
	public OrderInfo orderBread(OrderBreadRequest payload) throws OrderExceptionMsg {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderInfo cancelBreadOrder(CancelBreadOrderRequest payload) throws OrderExceptionMsg {
		return null;
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
		if(!cakes.containsKey(payload.getOrderId()))
			throw new OrderExceptionMsg("There is no order with id = " + payload.getOrderId() );
		return cakes.get(payload.getOrderId());
		
	}

}
