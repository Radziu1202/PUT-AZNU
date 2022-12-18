package org.bp.gate.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.bp.bread.CancelBreadOrderRequest;
import org.bp.bread.OrderBreadRequest;
import org.bp.bread.OrderBreadResponse;
import org.bp.bread.Person;
import org.bp.gate.BakeryIdentifierService;
import org.bp.CancelCakeOrderRequest;
import org.bp.OrderCakeRequest;
import org.bp.OrderInfo;
import org.bp.payment.PaymentRequest;
import org.bp.payment.PaymentResponse;

public class Utils {
    public static OrderCakeRequest prepareCakeOrderRequest(BakingRequest bakingRequest) {
        OrderCakeRequest orderCakeRequest = new OrderCakeRequest();
        if (bakingRequest.getOrderCakeRequest().getCake() != null) {
            orderCakeRequest.setCake(bakingRequest.getOrderCakeRequest().getCake());

        }
        if (bakingRequest.getOrderCakeRequest().getPerson() != null) {
            orderCakeRequest.setPerson(bakingRequest.getOrderCakeRequest().getPerson());

        }
        if (bakingRequest.getOrderCakeRequest().getDeliveryPlace() != null) {
            orderCakeRequest.setDeliveryPlace(bakingRequest.getOrderCakeRequest().getDeliveryPlace());

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
        if (bakingRequest.getOrderBreadRequest().getBread() != null) {
            orderBreadRequest.setBread(bakingRequest.getOrderBreadRequest().getBread());

        }
        if (bakingRequest.getOrderBreadRequest().getPerson() != null) {
            orderBreadRequest.setPerson(bakingRequest.getOrderBreadRequest().getPerson());

        }
        if (bakingRequest.getOrderBreadRequest().getDeliveryPlace() != null) {
            orderBreadRequest.setDeliveryPlace(bakingRequest.getOrderBreadRequest().getDeliveryPlace());

        }


        return orderBreadRequest;
    }

    public static PaymentRequest preparePaymentRequest(BakingRequest bakingRequest) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPaymentCard(bakingRequest.getPaymentRequest().getPaymentCard());
        String orderId = UUID.randomUUID().toString();
        paymentRequest.setOrderId(orderId);

        paymentRequest.setAmount(new BigDecimal(0));
        return paymentRequest;
    }

    public static PaymentResponse createPaymentResponse() {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setTransactionDate(new Date());
        paymentResponse.setTransactionId(UUID.randomUUID().toString());
        return paymentResponse;
    }
}
