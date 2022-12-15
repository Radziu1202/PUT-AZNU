package org.bp.onlinebakeryui;

import java.net.URL;

import javax.xml.namespace.QName;

import org.bp.OrderPreviewRequest;
import org.bp.breadbakeryservice.model.CancelBreadOrderRequest;
import org.bp.breadbakeryservice.model.CancelBreadOrderRequestResponse;
import org.bp.breadbakeryservice.model.OrderBreadRequest;
import org.bp.breadbakeryservice.model.OrderBreadResponse;
import org.bp.onlinebakery.OnlineBakery;
import org.bp.onlinebakery.OrderExceptionMsg;
import org.bp.paymentbakery.model.PaymentException;
import org.bp.paymentbakery.model.PaymentRequest;
import org.bp.paymentbakery.model.PaymentResponse;
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
public class BreadOrderingController {
	

	@Autowired
	RestTemplate restTemplate;
    

	@GetMapping("/orderBread")
	public String orderBreadForm(Model model) {
		OrderBreadRequest ocf = new OrderBreadRequest();
		model.addAttribute("orderBreadRequest", ocf);
		return "orderBread";
	}

	@GetMapping("/cancelBreadOrder")
	public String cancelBreadOrderForm(Model model) {
		CancelBreadOrderRequest ocf = new CancelBreadOrderRequest();
		model.addAttribute("cancelBreadOrderRequest", ocf);
		return "cancelBreadOrder";
	}

	@PostMapping("/cancelBreadOrder")
	public String cancelBreadOrder(@ModelAttribute CancelBreadOrderRequest cbor, Model model) {
		 System.out.println("Invoking bread order cancel...");
         CancelBreadOrderRequest cancleBreadOrderRequest = cbor;
         
         
         try {
        	 ResponseEntity<CancelBreadOrderRequestResponse> cancleBreadOrderRequest__return = restTemplate.postForEntity("http://localhost:8085/cancelBreadOrder", cancleBreadOrderRequest,
        			 CancelBreadOrderRequestResponse.class);
             System.out.println("cancelBreadOrder.result=" + cancleBreadOrderRequest__return);
             
             model.addAttribute("cancelBreadOrderRequestResponse", cancleBreadOrderRequest__return);
             return "resultCancelBreadOrder";

         } catch (HttpStatusCodeException e) {
             System.out.println("Expected exception: OrderException has occurred.");
             System.out.println(e.toString());
             OrderException oe = new OrderException();
             oe.setCode(326);
             oe.setError(e.getMessage());
         	OrderExceptionMsg fault = new OrderExceptionMsg("Cannot cancel order.", oe);
             model.addAttribute("orderExceptionMsg", fault);
             return "orderException";
         }

	

	}
	
	
	@PostMapping("/orderBread")
	public String orderBread(@ModelAttribute OrderBreadRequest obr, Model model)  {
		 System.out.println("Invoking bread order...");
		 OrderBreadRequest orderBreadRequest = obr;
         
         
         try {
        	 ResponseEntity<OrderBreadResponse>orderBreadRequest__return = restTemplate.postForEntity("http://localhost:8085/orderBread", orderBreadRequest,
        			 OrderBreadResponse.class);
             System.out.println("orderBread.result=" + orderBreadRequest__return);
             
             model.addAttribute("orderBreadResponse", orderBreadRequest__return.getBody());
             return "resultOrderBread";

         } catch (HttpStatusCodeException e) {
             System.out.println("Expected exception: BreadOrderException has occurred.");
             System.out.println(e.toString());
             OrderException oe = new OrderException();
             oe.setCode(325);
             oe.setError(e.getMessage());
             OrderExceptionMsg fault = new OrderExceptionMsg("Cannot create order.", oe);
             model.addAttribute("orderExceptionMsg", fault);
             return "orderException";
         }

	}

}
