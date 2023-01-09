package org.bp.onlinebakeryui;

import java.net.URL;

import org.bp.OrderPreviewRequest;
import org.bp.onlinebakery.OnlineBakery;
import org.bp.onlinebakery.OnlineBakeryEndpointService;
import org.bp.onlinebakery.OrderExceptionMsg;
import org.bp.onlinebakeryui.delivery.model.DeliveryPreviewRequest;
import org.bp.onlinebakeryui.delivery.model.DeliveryResponse;
import org.bp.types.OrderException;
import org.bp.types.OrderInfo;
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
public class DeliveryController {

	@Autowired
	RestTemplate restTemplate;
    
	
	
	@GetMapping("/deliveryPreview")
	public String deliveryPreviewForm(Model model) {
		DeliveryPreviewRequest  opr = new DeliveryPreviewRequest();
		model.addAttribute("deliveryPreviewRequest", opr);
		return "deliveryPreview";
	}

	@PostMapping("/deliveryPreview")
	public String deliveryPreview(@ModelAttribute DeliveryPreviewRequest opr, Model model) {
	

						 try {
				        	 ResponseEntity<DeliveryResponse>deliveryPreviewRequest__return = restTemplate.postForEntity("http://localhost:8088/deliveryPreview", opr,
				        			 DeliveryResponse.class);
				             System.out.println("deliveryPreview.result=" + deliveryPreviewRequest__return);
				             
				             model.addAttribute("deliveryResponse", deliveryPreviewRequest__return.getBody());
				             return "deliveryPreviewResult";

				         } catch (HttpStatusCodeException  exception) {
				        	 System.out.println("Expected exception: DeliveryException has occurred.");
				             System.out.println(exception.toString());
				             model.addAttribute("paymentException", exception);
				             return "deliveryException";
				         }
					
				
			}

	}

