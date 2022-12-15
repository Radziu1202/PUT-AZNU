package org.bp.breadbakeryservice.model;

import org.bp.types.DeliveryPlace;
import org.bp.types.Person;

public class OrderBreadRequest {

    protected Person person;
    protected DeliveryPlace deliveryPlace;
    protected Bread bread;
    
  
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person value) {
        this.person = value;
    }


    public DeliveryPlace getDeliveryPlace() {
        return deliveryPlace;
    }

 
    public void setDeliveryPlace(DeliveryPlace value) {
        this.deliveryPlace = value;
    }

   
    public Bread getBread() {
        return bread;
    }


    public void setBread(Bread value) {
        this.bread = value;
    }

}
