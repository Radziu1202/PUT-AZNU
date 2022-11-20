
package org.bp.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Bread complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Bread"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isGlutenFree" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="breadType"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Baguette" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Brioche" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Ciabatta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Focaccia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Rye" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Pita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Multigrain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bread", propOrder = {
    "isGlutenFree",
    "name",
    "breadType"
})
public class Bread {

    protected boolean isGlutenFree;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected Bread.BreadType breadType;

    /**
     * Gets the value of the isGlutenFree property.
     * 
     */
    public boolean isIsGlutenFree() {
        return isGlutenFree;
    }

    /**
     * Sets the value of the isGlutenFree property.
     * 
     */
    public void setIsGlutenFree(boolean value) {
        this.isGlutenFree = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the breadType property.
     * 
     * @return
     *     possible object is
     *     {@link Bread.BreadType }
     *     
     */
    public Bread.BreadType getBreadType() {
        return breadType;
    }

    /**
     * Sets the value of the breadType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bread.BreadType }
     *     
     */
    public void setBreadType(Bread.BreadType value) {
        this.breadType = value;
    }


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
     *         &lt;element name="Baguette" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Brioche" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Ciabatta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Focaccia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Rye" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Pita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Multigrain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "baguette",
        "brioche",
        "ciabatta",
        "focaccia",
        "rye",
        "pita",
        "multigrain"
    })
    public static class BreadType {

        @XmlElement(name = "Baguette")
        protected String baguette;
        @XmlElement(name = "Brioche")
        protected String brioche;
        @XmlElement(name = "Ciabatta")
        protected String ciabatta;
        @XmlElement(name = "Focaccia")
        protected String focaccia;
        @XmlElement(name = "Rye")
        protected String rye;
        @XmlElement(name = "Pita")
        protected String pita;
        @XmlElement(name = "Multigrain")
        protected String multigrain;

        /**
         * Gets the value of the baguette property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBaguette() {
            return baguette;
        }

        /**
         * Sets the value of the baguette property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBaguette(String value) {
            this.baguette = value;
        }

        /**
         * Gets the value of the brioche property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBrioche() {
            return brioche;
        }

        /**
         * Sets the value of the brioche property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBrioche(String value) {
            this.brioche = value;
        }

        /**
         * Gets the value of the ciabatta property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCiabatta() {
            return ciabatta;
        }

        /**
         * Sets the value of the ciabatta property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCiabatta(String value) {
            this.ciabatta = value;
        }

        /**
         * Gets the value of the focaccia property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFocaccia() {
            return focaccia;
        }

        /**
         * Sets the value of the focaccia property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFocaccia(String value) {
            this.focaccia = value;
        }

        /**
         * Gets the value of the rye property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRye() {
            return rye;
        }

        /**
         * Sets the value of the rye property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRye(String value) {
            this.rye = value;
        }

        /**
         * Gets the value of the pita property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPita() {
            return pita;
        }

        /**
         * Sets the value of the pita property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPita(String value) {
            this.pita = value;
        }

        /**
         * Gets the value of the multigrain property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMultigrain() {
            return multigrain;
        }

        /**
         * Sets the value of the multigrain property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMultigrain(String value) {
            this.multigrain = value;
        }

    }

}
