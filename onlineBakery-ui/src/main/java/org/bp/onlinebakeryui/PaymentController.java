package org.bp.onlinebakeryui;

import java.net.URL;

import javax.xml.namespace.QName;

import org.bp.onlinebakery.OnlineBakery;
import org.bp.onlinebakery.OnlineBakeryEndpointService;
import org.bp.paymentbakery.model.*;

import org.bp.types.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Controller
public class PaymentController {
	private static final QName CAKE_SERVICE_NAME = new QName("http://onlinebakery.bp.org/", "OnlineBakeryEndpointService");


	
	@Autowired
	RestTemplate restTemplate;
    
    
	@PostMapping("/paymentForm")
	public String paymentForm(@ModelAttribute OrderInfo oi,Model model) {
		PaymentRequest pr = new PaymentRequest();
		pr.setAmount(oi.getCost());
		pr.setOrderId(oi.getId());
		model.addAttribute("paymentRequest", pr);
		return "payment";
	}
	
	
	
	
	@PostMapping("/makePayment")
	public String makePayment(@ModelAttribute PaymentRequest ocf, Model model) {   
	     
	         System.out.println("Invoking payment...");
	         PaymentRequest pr = ocf;
	         try {
	        	 ResponseEntity<PaymentResponse> payment__return = restTemplate.postForEntity("http://localhost:8083/payment", pr,
	 					PaymentResponse.class);
	             System.out.println("payment.result=" + payment__return.getBody());
	             
	             model.addAttribute("paymentResponse", payment__return.getBody());
	             if (ocf.getOrderId().charAt(0)=='B') {
		             restTemplate.postForEntity("http://localhost:8085/payForOrder", pr.getOrderId(),void.class);
	             }else if(ocf.getOrderId().charAt(0)=='C') {
	         		URL wsdlURL = OnlineBakeryEndpointService.WSDL_LOCATION;
	
	         		OnlineBakeryEndpointService ss = new OnlineBakeryEndpointService(wsdlURL, CAKE_SERVICE_NAME);
	            	OnlineBakery port = ss.getOnlineBakeryEndpointPort();
	            	port.payForOrder( pr.getOrderId());
	             }
	             return "resultPayment";

	         } catch (HttpStatusCodeException e) {
	             System.out.println("Expected exception: PaymentException has occurred.");
	             System.out.println(e.toString());
	             model.addAttribute("paymentException", e);
	             return "paymentException";
	         }

	     
	
	}
	     
	



}