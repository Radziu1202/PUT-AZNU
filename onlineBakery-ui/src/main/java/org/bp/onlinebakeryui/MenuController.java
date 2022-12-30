package org.bp.onlinebakeryui;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.bp.mikrobrama.model.BakingRequest;
import org.bp.mikrobrama.model.BakingRequestResponse;
//import org.bp.mikrobrama.model.GetOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Controller
public class MenuController {
    private static final QName SERVICE_NAME = new QName("http://onlinebakery.bp.org/", "OnlineBakeryEndpointService");

    @Autowired
    RestTemplate restTemplate;

	@GetMapping("/menu")
	public String menuForm(Model model) {
		return "menu";
	}
	
	  @GetMapping("/orderBaking")
	    public String orderFurnitureForm(Model model){
	        model.addAttribute("bakingRequest", new BakingRequest());
	        return "orderBaking"; 
	    }

	    @PostMapping("/orderBaking")
	    public String orderFurniture(@ModelAttribute BakingRequest bakingRequest, Model model){
	        try{
	            ResponseEntity<BakingRequestResponse> response = restTemplate.postForEntity("http://localhost:8090/api/order/ordering",
	            		bakingRequest,
	            		BakingRequestResponse.class);
	            model.addAttribute("bakingRequestResponse", response.getBody());
	            return "resultBakingOrder";
	        }
	        catch (HttpStatusCodeException exception){
	            model.addAttribute("fault", exception);
	            return "fault";
	        }
	    }
/*
	    @GetMapping("/getOrder")
	    public String findOrderForm(Model model){
	        model.addAttribute(new GetOrderRequest());
	        return "getOrder";
	    }

	    @PostMapping("/getOrder")
	    public String findOrder(@ModelAttribute GetOrderRequest getOrderRequest, Model model){
	        try {
	            ResponseEntity<BakingRequestResponse> response = restTemplate.getForEntity(String.format("http://localhost:8081/api/orderFurniture/order/%s", getOrderRequest.getId()),
	            		BakingRequestResponse.class);
	            model.addAttribute("BakingRequestResponse", response.getBody());
	            return "bakingRequestResponse";
	        } catch(HttpStatusCodeException ex) {
	            model.addAttribute("fault", ex);
	            return "fault";
	        }
	    }
	     
	*/


	

}