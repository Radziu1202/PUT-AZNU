package org.bp.mikrobrama;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.bp.mikrobrama.model.BakingRequest;
import org.bp.mikrobrama.model.BakingRequestResponse;
import org.bp.mikrobrama.model.DeliveryResponse;
import org.bp.mikrobrama.model.OrderBreadResponse;
import org.bp.mikrobrama.model.OrderInfo;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
	private HashMap<String, PaymentData> payments;

	@PostConstruct
	void init() {
		payments = new HashMap<>();
	}

	public static class PaymentData {
		BakingRequest bakingRequest;
		BakingRequestResponse cakeOrderInfo;
		BakingRequestResponse breadOrderInfo;
		BakingRequestResponse deliveryResponse;
		
		

		public boolean isReady() {
			return bakingRequest != null && ((cakeOrderInfo != null && deliveryResponse != null) ||(breadOrderInfo != null && deliveryResponse != null) ) ;
		}
	}

	public synchronized boolean addBakingRequest(String bakingOrderId, BakingRequest bakingRequest) {
		PaymentData paymentData = getPaymentData(bakingOrderId);
		paymentData.bakingRequest = bakingRequest;
		return paymentData.isReady();
	}

	public synchronized boolean addOrderInfo(String bakingOrderId, BakingRequestResponse bakingRequestResponse, String serviceType) {
		PaymentData paymentData = getPaymentData(bakingOrderId);
		if (serviceType.equals("cake"))
			paymentData.cakeOrderInfo = bakingRequestResponse;
		if (serviceType.equals("bread"))
			paymentData.breadOrderInfo = bakingRequestResponse;
		if (serviceType.equals("delivery"))
			paymentData.deliveryResponse = bakingRequestResponse;
		return paymentData.isReady();
	}

	public synchronized PaymentData getPaymentData(String bakingOrderId) {
		PaymentData paymentData = payments.get(bakingOrderId);
		if (paymentData == null) {
			paymentData = new PaymentData();
			payments.put(bakingOrderId, paymentData);
		}
		return paymentData;
	}

}
