package org.bp.mikrobrama.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

//import org.bp.mikrobrama.model.CancelCakeOrderRequest;
import org.bp.types.OrderInfo;
//import org.bp.mikrobrama.model.CancelBreadOrderRequest;
import org.bp.mikrobrama.model.OrderBreadRequest;
import org.bp.mikrobrama.model.OrderBreadResponse;
import org.bp.mikrobrama.model.DeliveryRequest;
import org.bp.mikrobrama.model.DeliveryResponse;
import org.bp.mikrobrama.model.BakingRequest;
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

}
