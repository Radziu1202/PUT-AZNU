
package org.bp.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.bp.org/types}Cake"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cost" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="person" type="{http://www.bp.org/types}Person"/&gt;
 *         &lt;element name="deliveryPlace" type="{http://www.bp.org/types}DeliveryPlace"/&gt;
 *         &lt;element name="payment" type="{http://www.bp.org/types}PaymentCard"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInfo", propOrder = {
    "id",
    "cost",
    "person",
    "deliveryPlace",
    "payment"
})
public class OrderInfo
    extends Cake
{

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected BigDecimal cost;
    @XmlElement(required = true)
    protected Person person;
    @XmlElement(required = true)
    protected DeliveryPlace deliveryPlace;
    @XmlElement(required = true)
    protected PaymentCard payment;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the cost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Sets the value of the cost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCost(BigDecimal value) {
        this.cost = value;
    }

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
