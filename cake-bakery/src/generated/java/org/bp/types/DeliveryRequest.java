package org.bp.types;

import java.util.Date;

import org.bp.types.DeliveryPlace;


public class DeliveryRequest {
	
	private DeliveryPlace deliveryPlace;
	private String orderId;
	public DeliveryPlace getDeliveryPlace() {
		return deliveryPlace;
	}
	public void setDeliveryPlace(DeliveryPlace deliveryPlace) {
		this.deliveryPlace = deliveryPlace;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
