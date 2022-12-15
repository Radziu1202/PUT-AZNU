package org.bp.breadbakeryservice.model;

import java.math.BigDecimal;

import org.bp.types.Person;

public class CancelBreadOrderRequestResponse {
	private String id;
	private Person person;
	private BigDecimal cost;
	private Bread bread;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public Bread getBread() {
		return bread;
	}
	public void setBread(Bread bread) {
		this.bread = bread;
	}
	
	public  CancelBreadOrderRequestResponse(OrderBreadResponse obr) {
		this.setId(obr.getId());
		this.setPerson(obr.getPerson());
		this.setCost(obr.getCost());
		this.setBread(obr.getBread());
		
	}
}
