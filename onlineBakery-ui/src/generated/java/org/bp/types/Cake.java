
package org.bp.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Cake complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Cake"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isVegan" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="birthdayName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CakeType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cake", propOrder = {
    "isVegan",
    "birthdayName",
    "cakeType"
})
@XmlSeeAlso({
    OrderInfo.class
})
public class Cake {

    protected boolean isVegan;
    @XmlElement(required = true)
    protected String birthdayName;
    @XmlElement(name = "CakeType", required = true)
    protected String cakeType;

    /**
     * Gets the value of the isVegan property.
     * 
     */
    public boolean isIsVegan() {
        return isVegan;
    }

    /**
     * Sets the value of the isVegan property.
     * 
     */
    public void setIsVegan(boolean value) {
        this.isVegan = value;
    }

    /**
     * Gets the value of the birthdayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthdayName() {
        return birthdayName;
    }

    /**
     * Sets the value of the birthdayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthdayName(String value) {
        this.birthdayName = value;
    }

    /**
     * Gets the value of the cakeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCakeType() {
        return cakeType;
    }

    /**
     * Sets the value of the cakeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCakeType(String value) {
        this.cakeType = value;
    }

}
