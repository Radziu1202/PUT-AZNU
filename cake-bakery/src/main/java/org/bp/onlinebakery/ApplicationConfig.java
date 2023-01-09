package org.bp.onlinebakery;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.bp.state.ProcessingEvent;
import org.bp.state.ProcessingState;
import org.bp.state.StateMachineBuilder;
import org.bp.state.StateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;


@Configuration
public class ApplicationConfig {

	@Bean
	public ServletRegistrationBean<CXFServlet> dispatcherServlet() {
		return new ServletRegistrationBean<CXFServlet>(new CXFServlet(), "/soap-api/*");
	}
	
	@Bean
	@Primary
	public DispatcherServletPath dispatcherServletPathProvider() {
	    return () -> "";
	}

	@Bean(name=Bus.DEFAULT_BUS_ID)
	public SpringBus springBus(LoggingFeature loggingFeature) {
		
		SpringBus cxfBus = new  SpringBus();
		cxfBus.getFeatures().add(loggingFeature);
		
		return cxfBus;
	}

	@Bean
	public LoggingFeature loggingFeature() {
		
		LoggingFeature loggingFeature = new LoggingFeature();
		loggingFeature.setPrettyLogging(true);
		
		return loggingFeature;
	}

	@Bean
	public Endpoint endpoint(Bus bus, OnlineBakeryEndpoint onlineBakeryEndpoint) {

		EndpointImpl endpoint = new EndpointImpl(bus, onlineBakeryEndpoint);
		endpoint.publish("/service/onlineBakery");
		
		return endpoint;
	}
	
	
	 @Bean(name="basicStateMachineBuilder")
	    public StateMachineBuilder basicStateMachineBuilder() {
	    	StateMachineBuilder smb = new StateMachineBuilder();
	    	smb.initialState(ProcessingState.NONE)
	        .add(ProcessingState.NONE,ProcessingEvent.START,ProcessingState.STARTED)
	        .add(ProcessingState.STARTED,ProcessingEvent.FINISH,ProcessingState.FINISHED)
	    	.add(ProcessingState.NONE,ProcessingEvent.CANCEL,ProcessingState.CANCELLED)	            
	    	.add(ProcessingState.STARTED,ProcessingEvent.CANCEL,ProcessingState.CANCELLED)	            
	    	.add(ProcessingState.FINISHED,ProcessingEvent.CANCEL,ProcessingState.CANCELLED)	            
	    	.add(ProcessingState.CANCELLED,ProcessingEvent.START,ProcessingState.CANCELLED)	            
	    	.add(ProcessingState.CANCELLED,ProcessingEvent.FINISH,ProcessingState.CANCELLED)	            
	    	.add(ProcessingState.FINISHED,ProcessingEvent.COMPLETE,ProcessingState.COMPLETED)
			.add(ProcessingState.CANCELLED,ProcessingEvent.COMPLETE,ProcessingState.COMPLETED)	  	            
	         ;
	    	return smb;
	    }

	    @Bean
	    @Scope("prototype")
	    public StateService stateService(@Qualifier("basicStateMachineBuilder") StateMachineBuilder stateMachineBuilder) {
	    	return new StateService (stateMachineBuilder);
	    }

}