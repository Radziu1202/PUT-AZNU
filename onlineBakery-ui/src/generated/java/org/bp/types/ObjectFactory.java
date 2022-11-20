
package org.bp.types;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.bp.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.bp.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Bread }
     * 
     */
    public Bread createBread() {
        return new Bread();
    }

    /**
     * Create an instance of {@link OrderInfo }
     * 
     */
    public OrderInfo createOrderInfo() {
        return new OrderInfo();
    }

    /**
     * Create an instance of {@link Cake }
     * 
     */
    public Cake createCake() {
        return new Cake();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link DeliveryPlace }
     * 
     */
    public DeliveryPlace createDeliveryPlace() {
        return new DeliveryPlace();
    }

    /**
     * Create an instance of {@link PaymentCard }
     * 
     */
    public PaymentCard createPaymentCard() {
        return new PaymentCard();
    }

    /**
     * Create an instance of {@link OrderException }
     * 
     */
    public OrderException createOrderException() {
        return new OrderException();
    }

    /**
     * Create an instance of {@link Bread.BreadType }
     * 
     */
    public Bread.BreadType createBreadBreadType() {
        return new Bread.BreadType();
    }

}
