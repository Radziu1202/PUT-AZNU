package org.bp.paymentbakery.model;

import java.util.Date;

public class PaymentResponse {
	private String transactionId;
	private Date transactionDate;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	
}
