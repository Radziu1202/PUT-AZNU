//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.12.15 at 09:42:57 PM CET 
//


package org.bp.cake;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.bp.cake package. 
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

    private final static QName _CancelCakeOrderResponse_QNAME = new QName("http://www.bp.org", "cancelCakeOrderResponse");
    private final static QName _OrderCakeResponse_QNAME = new QName("http://www.bp.org", "orderCakeResponse");
    private final static QName _OrderException_QNAME = new QName("http://www.bp.org", "orderException");
    private final static QName _OrderIdRequest_QNAME = new QName("http://www.bp.org", "orderIdRequest");
    private final static QName _OrderPreviewResponse_QNAME = new QName("http://www.bp.org", "orderPreviewResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.bp.cake
     * 
     */
    public ObjectFactory() {
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
     * Create an instance of {@link CancelCakeOrderRequest }
     * 
     */
    public CancelCakeOrderRequest createCancelCakeOrderRequest() {
        return new CancelCakeOrderRequest();
    }

    /**
     * Create an instance of {@link OrderCakeRequest }
     * 
     */
    public OrderCakeRequest createOrderCakeRequest() {
        return new OrderCakeRequest();
    }

    /**
     * Create an instance of {@link OrderPreviewRequest }
     * 
     */
    public OrderPreviewRequest createOrderPreviewRequest() {
        return new OrderPreviewRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrderInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.bp.org", name = "cancelCakeOrderResponse")
    public JAXBElement<OrderInfo> createCancelCakeOrderResponse(OrderInfo value) {
        return new JAXBElement<OrderInfo>(_CancelCakeOrderResponse_QNAME, OrderInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrderInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.bp.org", name = "orderCakeResponse")
    public JAXBElement<OrderInfo> createOrderCakeResponse(OrderInfo value) {
        return new JAXBElement<OrderInfo>(_OrderCakeResponse_QNAME, OrderInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrderException }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.bp.org", name = "orderException")
    public JAXBElement<OrderException> createOrderException(OrderException value) {
        return new JAXBElement<OrderException>(_OrderException_QNAME, OrderException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.bp.org", name = "orderIdRequest")
    public JAXBElement<String> createOrderIdRequest(String value) {
        return new JAXBElement<String>(_OrderIdRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrderInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.bp.org", name = "orderPreviewResponse")
    public JAXBElement<OrderInfo> createOrderPreviewResponse(OrderInfo value) {
        return new JAXBElement<OrderInfo>(_OrderPreviewResponse_QNAME, OrderInfo.class, null, value);
    }

}
