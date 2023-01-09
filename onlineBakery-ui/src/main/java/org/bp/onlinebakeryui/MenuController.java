package org.bp.onlinebakeryui;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.bp.CancelCakeOrderRequest;
import org.bp.OrderPreviewRequest;
import org.bp.mikrobrama.model.BakingRequest;
import org.bp.mikrobrama.model.BakingRequestResponse;
import org.bp.onlinebakery.OrderExceptionMsg;
import org.bp.onlinebakery.OrderPreviewExceptionMsg;
import org.bp.types.Cake;
import org.bp.types.DeliveryPlace;
import org.bp.types.OrderException;
import org.bp.types.OrderPreviewException;
import org.bp.types.Person;
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
		  BakingRequest br =  new BakingRequest();
		  br.setPerson(new Person());
		  br.setCake(new Cake());
		  br.setDeliveryPlace(new DeliveryPlace());
	        model.addAttribute("bakingRequest",br);
	        return "orderBaking"; 
	    }

	    @PostMapping("/orderBaking")
	    public String orderBaking(@ModelAttribute BakingRequest bakingRequest, Model model){
	        try{
	            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8090/api/order/ordering",
	            		bakingRequest,
	            		String.class);
	            model.addAttribute("orderId", response.getBody());
	            return "resultBakingOrder";
	        }
	        catch (HttpStatusCodeException exception){
	            model.addAttribute("fault", exception);
	            return "fault";
	        }
	    }

	    @GetMapping("/orderPreview")
	    public String findOrderForm(Model model){
	        model.addAttribute(new OrderPreviewRequest());
	        return "orderPreview";
	    }

	    @PostMapping("/orderPreview")
	    public String findOrder(@ModelAttribute OrderPreviewRequest orderPreviewRequest, Model model){
	        try {
	            ResponseEntity<BakingRequestResponse> response = restTemplate.postForEntity("http://localhost:8090/api/order/orderPreview", orderPreviewRequest,
	            		BakingRequestResponse.class);
	            BakingRequestResponse brr = response.getBody();
	            if ( brr.getOrderCakeResponse()== null) {
	            	OrderPreviewException oe = new OrderPreviewException();
	    			oe.setCode(321);
	    			oe.setError("There is no order with that id");
	    			OrderPreviewExceptionMsg fault = new OrderPreviewExceptionMsg("Cannot find order.", oe);
	    			
	            	throw fault;
	            }
	            model.addAttribute("bakingRequestResponse",brr );
	            return "bakingPreview";
	        } catch(OrderPreviewExceptionMsg ex) {
	            model.addAttribute("orderExceptionMsg", ex);
	            return "orderException";
	        }
	    }
	     
	
	    @GetMapping("/cancelOrder")
		public String cancelOrderForm(Model model) {
	    	OrderPreviewRequest opr = new OrderPreviewRequest();
			model.addAttribute("orderPreviewRequest", opr);
			return "cancelCakeOrder";
		}

	    
	    

	    @PostMapping("/cancelOrder")
	    public String cancelOrder(@ModelAttribute OrderPreviewRequest orderPreviewRequest, Model model){
	        try{
	            ResponseEntity<BakingRequestResponse> response = restTemplate.postForEntity("http://localhost:8090/api/order/cancelOrder",
	            		orderPreviewRequest,
	            		BakingRequestResponse.class);
	            BakingRequestResponse brr = response.getBody();
	            if ( brr.getOrderCakeResponse()== null) {
	            	OrderPreviewException oe = new OrderPreviewException();
	    			oe.setCode(321);
	    			oe.setError("There is no order with that id");
	    			OrderPreviewExceptionMsg fault = new OrderPreviewExceptionMsg("Cannot find order.", oe);
	    			
	            	throw fault;
	            }
	            model.addAttribute("bakingRequestResponse", brr);
	            return "bakingPreviewCancel";
	        }
	        catch (OrderPreviewExceptionMsg exception){
	        	  model.addAttribute("orderExceptionMsg", exception);
		            return "orderException";
	        }
	    }


}