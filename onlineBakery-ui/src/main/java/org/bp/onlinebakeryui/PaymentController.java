package org.bp.onlinebakeryui;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.bp.OrderCakeRequest;
import org.bp.onlinebakery.OnlineBakery;
import org.bp.onlinebakery.OnlineBakeryEndpointService;
import org.bp.paymentbakery.model.*;

import org.bp.types.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class PaymentController {
    private static final QName SERVICE_NAME = new QName("http://onlinebakery.bp.org/", "OnlineBakeryEndpointService");


	
	@Autowired
	RestTemplate restTemplate;
    
    
	@PostMapping("/paymentForm")
	public String paymentForm(@ModelAttribute OrderInfo oi,Model model) {
		PaymentRequest pr = new PaymentRequest();
		Amount amount = new Amount();
		amount.setValue(oi.getCost());
		pr.setAmount(amount);
		model.addAttribute("paymentRequest", pr);
		return "payment";
	}
	
	
	
	
	@PostMapping("/makePayment")
	public String makePayment(@ModelAttribute PaymentRequest ocf, Model model) {
		
		URL wsdlURL = OnlineBakeryEndpointService.WSDL_LOCATION;
		 
		 OnlineBakeryEndpointService ss = new OnlineBakeryEndpointService(wsdlURL, SERVICE_NAME);
	     OnlineBakery port = ss.getOnlineBakeryEndpointPort();
		
	     
	     {
	         System.out.println("Invoking payment...");
	         PaymentRequest pr = ocf;
	         try {
	        	 ResponseEntity<PaymentResponse> payment__return = restTemplate.postForEntity("http://localhost:8083/payment", pr,
	 					PaymentResponse.class);
	             System.out.println("payment.result=" + payment__return);
	             
	             model.addAttribute("paymentResponse", payment__return);
	             return "resultPayment";

	         } catch (PaymentException e) {
	             System.out.println("Expected exception: PaymentException has occurred.");
	             System.out.println(e.toString());
	             model.addAttribute("PaymentException", e);
	             return "paymentException";
	         }

	     }

		
		
	}
	     
	



}