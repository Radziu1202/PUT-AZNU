package org.bp.gate.model;

import org.bp.bread.OrderBreadResponse;
import org.bp.OrderInfo;


public class BakingRequestResponse {
    private String id;
    private OrderBreadResponse orderBreadResponse;
    private OrderInfo orderCakeResponse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderBreadResponse getOrderBreadResponse() {
        return orderBreadResponse;
    }

    public void setOrderBreadResponse(OrderBreadResponse orderBreadResponse) {
        this.orderBreadResponse = orderBreadResponse;
    }

    public OrderInfo getOrderCakeResponse() {
        return orderCakeResponse;
    }

    public void setOrderCakeResponse(OrderInfo orderCakeResponse) {
        this.orderCakeResponse = orderCakeResponse;
    }
}

