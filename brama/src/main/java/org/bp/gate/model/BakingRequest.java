package org.bp.gate.model;

import org.bp.bread.OrderBreadRequest;
import org.bp.OrderCakeRequest;
import org.bp.payment.PaymentRequest;


public class BakingRequest {
    private OrderBreadRequest orderBreadRequest;
    private OrderCakeRequest orderCakeRequest;
    private PaymentRequest paymentRequest;

    public OrderBreadRequest getOrderBreadRequest() {
        return orderBreadRequest;
    }

    public void setsetOrderCakeRequest(OrderBreadRequest orderBreadRequest) {
        this.orderBreadRequest = orderBreadRequest;
    }

    public OrderCakeRequest getOrderCakeRequest() {
        return orderCakeRequest;
    }

    public void setOrderCakeRequest(OrderCakeRequest orderCakeRequest) {
        this.orderCakeRequest = orderCakeRequest;
    }


    public PaymentRequest getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(PaymentRequest paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

}
