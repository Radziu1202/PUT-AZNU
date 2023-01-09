package org.bp.onlinebakery;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.bp.CancelCakeOrderRequest;
//import org.bp.mikrobrama.model.CancelCakeOrderRequest;
import org.bp.OrderCakeRequest;
import org.bp.types.BakingRequest;
import org.bp.types.BakingRequestResponse;
import org.bp.types.OrderInfo;
//import org.bp.mikrobrama.model.CancelBreadOrderRequest;

//import org.bp.mikrobrama.model.PaymentRequest;
//import org.bp.mikrobrama.model.PaymentResponse;



public class Utils {
	static public BakingRequestResponse prepareBakingRequestResponse(String bookingId, BigDecimal cost) {
		BakingRequestResponse bakingRequestResponse = new BakingRequestResponse();
		OrderInfo oi = new OrderInfo();
		bakingRequestResponse.setId(bookingId);
		oi.setId(bookingId);
		oi.setCost(cost);
		bakingRequestResponse.setOrderCakeResponse(oi);
		return bakingRequestResponse;
	}
	
	
	 public static OrderCakeRequest prepareCakeOrderRequest(BakingRequest bakingRequest) {
	        OrderCakeRequest orderCakeRequest = new OrderCakeRequest();
	        if (bakingRequest.getCake() != null) {
	            orderCakeRequest.setCake(bakingRequest.getCake());

	        }
	        if (bakingRequest.getPerson() != null) {
	            orderCakeRequest.setPerson(bakingRequest.getPerson());

	        }
	        if (bakingRequest.getDeliveryPlace() != null) {
	            orderCakeRequest.setDeliveryPlace(bakingRequest.getDeliveryPlace());

	        }
	        


	        return orderCakeRequest;
	    }

	    public static CancelCakeOrderRequest prepareCancelCakeOrderRequest(OrderInfo orderInfo) {
	        CancelCakeOrderRequest cancelcakeOrderRequest = new CancelCakeOrderRequest();
	        cancelcakeOrderRequest.setOrderId(orderInfo.getId());
	        return cancelcakeOrderRequest;
	    }


}
