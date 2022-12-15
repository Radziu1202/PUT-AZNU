package org.bp.onlinebakeryui;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuController {
    private static final QName SERVICE_NAME = new QName("http://onlinebakery.bp.org/", "OnlineBakeryEndpointService");

    
	@GetMapping("/menu")
	public String menuForm(Model model) {
		return "menu";
	}
	
	
	     
	


	

}