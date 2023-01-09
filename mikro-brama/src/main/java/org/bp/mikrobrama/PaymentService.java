package org.bp.mikrobrama;

import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bp.mikrobrama.model.BakingRequest;
import org.bp.mikrobrama.model.BakingRequestResponse;
import org.bp.mikrobrama.model.DeliveryResponse;
import org.bp.mikrobrama.model.OrderBreadResponse;
import org.bp.types.OrderInfo;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
	 private static final Logger LOGGER = LogManager.getLogger(PaymentService.class);
		
		private HashMap<String, PaymentData> payments;
		
		@PostConstruct
		void init() {
			payments=new HashMap<>();
		}
		

	public static class PaymentData {
		BakingRequest bakingRequest;
		BakingRequestResponse bakingRequestResponse;

		
		

		public boolean isReady() {
			return bakingRequest != null && ((bakingRequestResponse.getOrderCakeResponse() != null && bakingRequestResponse.getOrderDeliveryResponse() != null) ||(bakingRequestResponse.getOrderBreadResponse() != null && bakingRequestResponse.getOrderDeliveryResponse() != null) ) ;
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
			paymentData.bakingRequestResponse.setOrderCakeResponse(bakingRequestResponse.getOrderCakeResponse()); ;
		if (serviceType.equals("bread"))
			paymentData.bakingRequestResponse.setOrderBreadResponse(bakingRequestResponse.getOrderBreadResponse()); ;
		if (serviceType.equals("delivery"))
			paymentData.bakingRequestResponse.setOrderDeliveryResponse(bakingRequestResponse.getOrderDeliveryResponse()); ;
		
		LOGGER.error("-------------------paymentData po dodaniu ------- " + paymentData.bakingRequestResponse);

		return paymentData.isReady();
	}

	public synchronized PaymentData getPaymentData(String bakingOrderId) {
		LOGGER.error("-------------------size ------- " + payments.size());
		
		Iterator<String> namesIterator = payments.keySet().iterator();
		while(namesIterator.hasNext()) {
			LOGGER.error("-------------------element:  ------- " + payments.get(namesIterator.next() ));
			}
		
		PaymentData paymentData = payments.get(bakingOrderId);
		if (paymentData == null) {
			paymentData = new PaymentData();
			paymentData.bakingRequestResponse= new BakingRequestResponse();
			payments.put(bakingOrderId, paymentData);
		}
		return paymentData;
	}

}
