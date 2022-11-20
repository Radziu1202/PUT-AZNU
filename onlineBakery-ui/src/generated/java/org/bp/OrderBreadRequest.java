
package org.bp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.bp.types.Bread;
import org.bp.types.DeliveryPlace;
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
 *         &lt;element name="bread" type="{http://www.bp.org/types}Bread"/&gt;
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
    "bread"
})
@XmlRootElement(name = "orderBreadRequest")
public class OrderBreadRequest {

    @XmlElement(required = true)
    protected Person person;
    @XmlElement(required = true)
    protected DeliveryPlace deliveryPlace;
    @XmlElement(required = true)
    protected Bread bread;

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
     * Gets the value of the bread property.
     * 
     * @return
     *     possible object is
     *     {@link Bread }
     *     
     */
    public Bread getBread() {
        return bread;
    }

    /**
     * Sets the value of the bread property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bread }
     *     
     */
    public void setBread(Bread value) {
        this.bread = value;
    }

}
