package org.bp.gate.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.bp.bread.CancelBreadOrderRequest;
import org.bp.bread.OrderBreadRequest;
import org.bp.bread.OrderBreadResponse;
import org.bp.delivery.DeliveryRequest;
import org.bp.delivery.DeliveryResponse;
import org.bp.gate.BakeryIdentifierService;
import org.bp.CancelCakeOrderRequest;
import org.bp.OrderCakeRequest;
import org.bp.OrderInfo;
import org.bp.payment.PaymentRequest;
import org.bp.payment.PaymentResponse;

public class Utils {
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

    public static CancelBreadOrderRequest prepareBreadOrderCancelRequest(OrderBreadResponse orderBreadResponse) {
        CancelBreadOrderRequest cancelBreadOrderRequest = new CancelBreadOrderRequest();
        cancelBreadOrderRequest.setOrderId(orderBreadResponse.getId());
        return cancelBreadOrderRequest;
    }

    public static OrderBreadRequest prepareOrderBreadRequest(BakingRequest bakingRequest) {
        OrderBreadRequest orderBreadRequest = new OrderBreadRequest();
        if (bakingRequest.getBread() != null) {
            orderBreadRequest.setBread(bakingRequest.getBread());

        }
        if (bakingRequest.getPerson() != null) {
            orderBreadRequest.setPerson(bakingRequest.getPerson());

        }
        if (bakingRequest.getDeliveryPlace() != null) {
            orderBreadRequest.setDeliveryPlace(bakingRequest.getDeliveryPlace());

        }


        return orderBreadRequest;
    }

    public static PaymentRequest preparePaymentRequest(BakingRequest bakingRequest) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPaymentCard(bakingRequest.getPaymentCard());
       
        paymentRequest.setAmount(new BigDecimal(0));
        return paymentRequest;
    }

    public static PaymentResponse createPaymentResponse() {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setTransactionDate(new Date());
        paymentResponse.setTransactionId(UUID.randomUUID().toString());
        return paymentResponse;
    }
   
    
    public static DeliveryRequest prepareDeliveryRequest(BakingRequest bakingRequest) {
    	DeliveryRequest deliveryRequest = new DeliveryRequest();
    	deliveryRequest.setDeliveryPlace(bakingRequest.getDeliveryPlace());    	

        return deliveryRequest;
    }

    public static DeliveryResponse createDeliveryResponse() {
    	DeliveryResponse deliveryResponse = new DeliveryResponse();
    	Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		
    	deliveryResponse.setDeliveryDate(dt);
    	//deliveryResponse.setDeliveryPlace(null);
    	deliveryResponse.setDeliveryId(UUID.randomUUID().toString());
    	//deliveryResponse.setOrderId(null);
        return deliveryResponse;
    }
}
