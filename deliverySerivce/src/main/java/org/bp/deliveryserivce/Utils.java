package org.bp.deliveryserivce;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.bp.types.BakingRequest;
import org.bp.types.DeliveryRequest;
import org.bp.types.DeliveryResponse;




public class Utils {
    
    
    public static DeliveryRequest prepareDeliveryRequest(BakingRequest bakingRequest) {
    	DeliveryRequest deliveryRequest = new DeliveryRequest();
    	deliveryRequest.setDeliveryPlace(bakingRequest.getDeliveryPlace());    	

        return deliveryRequest;
    }

    public static DeliveryResponse createDeliveryResponse() {
    	DeliveryResponse deliveryResponse = new DeliveryResponse();
    	Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		
    	deliveryResponse.setDeliveryDate(dt);
    	//deliveryResponse.setDeliveryPlace(null);
    	deliveryResponse.setDeliveryId(UUID.randomUUID().toString());
    	//deliveryResponse.setOrderId(null);
        return deliveryResponse;
    }
}
