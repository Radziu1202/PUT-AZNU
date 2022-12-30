package org.bp.mikrobrama.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;



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
