package org.bp.gate.model;

import org.bp.bread.Bread;
import org.bp.bread.OrderBreadRequest;
import org.bp.delivery.DeliveryRequest;
import org.bp.Cake;
import org.bp.DeliveryPlace;
import org.bp.OrderCakeRequest;
import org.bp.Person;
import org.bp.payment.PaymentCard;
import org.bp.payment.PaymentRequest;


public class BakingRequest {
    private Bread bread;
    private Cake cake;
    private DeliveryPlace deliveryPlace;
    private Person person;
    private PaymentCard paymentCard;
   
	public Bread getBread() {
		return bread;
	}

	public void setBread(Bread bread) {
		this.bread = bread;
	}

	public Cake getCake() {
		return cake;
	}

	public void setCake(Cake cake) {
		this.cake = cake;
	}


	public DeliveryPlace getDeliveryPlace() {
		return deliveryPlace;
	}

	public void setDeliveryPlace(DeliveryPlace deliveryPlace) {
		this.deliveryPlace = deliveryPlace;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PaymentCard getPaymentCard() {
		return paymentCard;
	}

	public void setPaymentCard(PaymentCard paymentCard) {
		this.paymentCard = paymentCard;
	}

}
