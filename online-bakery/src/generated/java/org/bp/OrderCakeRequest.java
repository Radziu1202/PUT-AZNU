
package org.bp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.bp.types.Cake;
import org.bp.types.DeliveryPlace;
import org.bp.types.PaymentCard;
import org.bp.types.Person;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="person" type="{http://www.bp.org/types}Person"/&gt;
 *         &lt;element name="deliveryPlace" type="{http://www.bp.org/types}DeliveryPlace"/&gt;
 *         &lt;element name="cake" type="{http://www.bp.org/types}Cake"/&gt;
 *         &lt;element name="payment" type="{http://www.bp.org/types}PaymentCard"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "person",
    "deliveryPlace",
    "cake",
    "payment"
})
@XmlRootElement(name = "orderCakeRequest")
public class OrderCakeRequest {

    @XmlElement(required = true)
    protected Person person;
    @XmlElement(required = true)
    protected DeliveryPlace deliveryPlace;
    @XmlElement(required = true)
    protected Cake cake;
    @XmlElement(required = true)
    protected PaymentCard payment;

    /**
     * Gets the value of the person property.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setPerson(Person value) {
        this.person = value;
    }

    /**
     * Gets the value of the deliveryPlace property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryPlace }
     *     
     */
    public DeliveryPlace getDeliveryPlace() {
        return deliveryPlace;
    }

    /**
     * Sets the value of the deliveryPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryPlace }
     *     
     */
    public void setDeliveryPlace(DeliveryPlace value) {
        this.deliveryPlace = value;
    }

    /**
     * Gets the value of the cake property.
     * 
     * @return
     *     possible object is
     *     {@link Cake }
     *     
     */
    public Cake getCake() {
        return cake;
    }

    /**
     * Sets the value of the cake property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cake }
     *     
     */
    public void setCake(Cake value) {
        this.cake = value;
    }

    /**
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentCard }
     *     
     */
    public PaymentCard getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentCard }
     *     
     */
    public void setPayment(PaymentCard value) {
        this.payment = value;
    }

}
