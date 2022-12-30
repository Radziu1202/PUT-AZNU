package org.bp.onlinebakeryui.delivery.model;

import java.util.Date;


public class DeliveryResponse {

	private Date deliveryDate;
	private DeliveryPlace deliveryPlace;
	private String deliveryId;
	private String orderId;

	public DeliveryPlace getDeliveryPlace() {
		return deliveryPlace;
	}
	public void setDeliveryPlace(DeliveryPlace deliveryPlace) {
		this.deliveryPlace = deliveryPlace;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
