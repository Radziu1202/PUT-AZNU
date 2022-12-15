package org.bp.gate.model;
import org.bp.bread.OrderBreadRequest;
import org.bp.cake.OrderCakeRequest;


public class BakingRequest {
    private OrderBreadRequest orderBreadRequest;
    private OrderCakeRequest orderCakeRequest;

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
}
