package org.bp.gate;

import org.springframework.stereotype.Service;

@Service
public class BakeryIdentifierService {
	
	
	
	public static class TransactionIds{
		private String cakeId;
		private String breadId;
		private String paymentId;
		
		public String getCakeId() {
			return cakeId;
		}
		public void setCakeId(String cakeId) {
			this.cakeId = cakeId;
		}
		
		public String getBreadId() {
			return breadId;
		}
		public void setCakesId(String breadId) {
			this.breadId = breadId;
		}
		
		public String getPaymnetId() {
			return paymentId;
		}
		public void setPaymentId(String paymentId) {
			this.paymentId = paymentId;
		}
		
		
	}

}
