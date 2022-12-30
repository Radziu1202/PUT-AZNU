package org.bp.onlinebakeryui.delivery.model;




public class DeliveryPlace {

    protected String postalCode;
    protected String city;
    protected String address;

   
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String value) {
        this.postalCode = value;
    }

   
    public String getCity() {
        return city;
    }

   
    public void setCity(String value) {
        this.city = value;
    }

  
    public String getAddress() {
        return address;
    }


    public void setAddress(String value) {
        this.address = value;
    }

}
