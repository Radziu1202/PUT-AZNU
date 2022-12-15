package org.bp.onlinebakeryui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.bp.CancelCakeOrderRequest;
import org.bp.OrderCakeRequest;
import org.bp.OrderPreviewRequest;
import org.bp.breadbakeryservice.model.BreadOrderException;
import org.bp.breadbakeryservice.model.CancelBreadOrderRequestResponse;
import org.bp.breadbakeryservice.model.OrderBreadResponse;
import org.bp.onlinebakery.OnlineBakery;
import org.bp.onlinebakery.OnlineBakeryEndpoint;
import org.bp.onlinebakery.OnlineBakeryEndpointService;
import org.bp.onlinebakery.OrderExceptionMsg;
import org.bp.onlinebakeryui.*;
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
public class CakeOrderingController {
	private static final QName SERVICE_NAME = new QName("http://onlinebakery.bp.org/", "OnlineBakeryEndpointService");
	
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/orderCake")
	public String orderCakeForm(Model model) {
		OrderCakeRequest ocf = new OrderCakeRequest();
		model.addAttribute("orderCakeRequest", ocf);
		return "orderCake";
	}

	@GetMapping("/cancelCakeOrder")
	public String cancelCakeOrderForm(Model model) {
		CancelCakeOrderRequest ocf = new CancelCakeOrderRequest();
		model.addAttribute("cancelCakeOrderRequest", ocf);
		return "cancelCakeOrder";
	}

	@PostMapping("/cancelCakeOrder")
	public String cancelCakeOrder(@ModelAttribute CancelCakeOrderRequest ocf, Model model) {
		URL wsdlURL = OnlineBakeryEndpointService.WSDL_LOCATION;

		OnlineBakeryEndpointService ss = new OnlineBakeryEndpointService(wsdlURL, SERVICE_NAME);
		OnlineBakery port = ss.getOnlineBakeryEndpointPort();

		{
			System.out.println("Invoking cancelCakeOrder...");
			org.bp.CancelCakeOrderRequest _cancelCakeOrder_payload = ocf;
			try {
				OrderInfo _cancelCakeOrder__return = port.cancelCakeOrder(_cancelCakeOrder_payload);
				System.out.println("cancelOrder.result=" + _cancelCakeOrder__return);
				model.addAttribute("orderInfo", _cancelCakeOrder__return);
				return "result";

			} catch (OrderExceptionMsg e) {
				try {
					 ResponseEntity<CancelBreadOrderRequestResponse> cancleBreadOrderRequest__return = restTemplate.postForEntity("http://localhost:8085/cancelBreadOrder", ocf,
			 					CancelBreadOrderRequestResponse.class);
			             System.out.println("cancelOrder.result=" + cancleBreadOrderRequest__return);
			             
			             model.addAttribute("orderInfo", cancleBreadOrderRequest__return);
			             return "result";
				}catch (BreadOrderException exception) {
		             System.out.println("Expected exception: OrderException has occurred.");
		             System.out.println(exception.toString());
		             model.addAttribute("orderExceptionMsg", exception);
		             return "orderException";
		         }
			}

		}

	}

	@PostMapping("/orderCake")
	public String orderCake(@ModelAttribute OrderCakeRequest ocf, Model model) {
		URL wsdlURL = OnlineBakeryEndpointService.WSDL_LOCATION;

		OnlineBakeryEndpointService ss = new OnlineBakeryEndpointService(wsdlURL, SERVICE_NAME);
		OnlineBakery port = ss.getOnlineBakeryEndpointPort();

		{
			System.out.println("Invoking orderCake...");
			org.bp.OrderCakeRequest _orderCake_payload = ocf;
			try {
				OrderInfo _orderCake__return = port.orderCake(_orderCake_payload);
				System.out.println("orderCake.result=" + _orderCake__return);
				model.addAttribute("orderInfo", _orderCake__return);
				return "result";

			} catch (OrderExceptionMsg e) {
				System.out.println("Expected exception: OrderExceptionMsg has occurred.");
				System.out.println(e.toString());
				model.addAttribute("orderExceptionMsg", e);
				return "orderException";
			}

		}

	}

	@GetMapping("/orderPreview")
	public String orderPreviewForm(Model model) {
		OrderPreviewRequest  opr = new OrderPreviewRequest();
		model.addAttribute("orderPreviewRequest", opr);
		return "orderPreview";
	}

	@PostMapping("/orderPreview")
	public String orderPreview(@ModelAttribute OrderPreviewRequest opr, Model model) {
		URL wsdlURL = OnlineBakeryEndpointService.WSDL_LOCATION;

		OnlineBakeryEndpointService ss = new OnlineBakeryEndpointService(wsdlURL, SERVICE_NAME);
		OnlineBakery port = ss.getOnlineBakeryEndpointPort();
		{
			System.out.println("Invoking orderPreview...");
			OrderPreviewRequest _orderPreview_payload = opr;
			try {
				OrderInfo _orderPreview__return = port.orderPreview(_orderPreview_payload);
					System.out.println("orderPreview.result=" + _orderPreview__return);
					model.addAttribute("orderInfo", _orderPreview__return);
					return "orderPreviewResult";

			
			} catch (OrderExceptionMsg e) {
				

						 try {
				        	 ResponseEntity<OrderBreadResponse>orderBreadRequest__return = restTemplate.postForEntity("http://localhost:8085/orderPreview", opr,
				        			 OrderBreadResponse.class);
				             System.out.println("orderPreview.result=" + orderBreadRequest__return);
				             
				             model.addAttribute("orderInfo", orderBreadRequest__return.getBody());
				             return "orderPreviewResult";

				         } catch (HttpStatusCodeException  exception) {
				            System.out.println("Expected exception: BreadOrderException has occurred.");
				            System.out.println(exception.toString());
				            
				            OrderException oe = new OrderException();
				           
				 			oe.setCode(321);
				 			oe.setError("there is no order with id =" + opr.getOrderId() );
				 			OrderExceptionMsg fault = new OrderExceptionMsg("Cannot find order.", oe);
				            model.addAttribute("orderExceptionMsg", fault);
				            return "orderException";
				         }
					
				
			}

		}

	}
	

}