package org.bp.gate;

import org.apache.camel.builder.RouteBuilder;
import static org.apache.camel.model.rest.RestParamType.body;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.bp.gate.model.BakingRequest;
import org.bp.gate.model.BakingRequestResponse;
import org.bp.gate.model.GateException;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

public class ManagmentService extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration()
		.component("servlet")
		.bindingMode(RestBindingMode.json)
		.dataFormatProperty("prettyPrint", "true")
		.enableCORS(true)
		.contextPath("/api")
		// turn on swagger api-doc
		.apiContextPath("/api-doc")
		.apiProperty("api.title", "Online Bakery API")
		.apiProperty("api.version", "1.0.0");
		
		
		 rest("/orderBaking").description("Order Baking") // tu przyjmuje endpoint
         .consumes("application/json")
         .produces("application/json")

         .post("/order").description("online bakery").type(BakingRequest.class).outType(BakingRequestResponse.class)
         .param().name("body").type(body).description("Furniture order param").endParam()
         .responseMessage().code(200).message("The furniture order ordered successfully").endResponseMessage()
         .responseMessage().code(400).responseModel(GateException.class).message("Post order exception").endResponseMessage()
         .to("direct:orderFurniture");
		
		
		
	}

}
