
package org.bp;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.bp.types.OrderException;
import org.bp.types.OrderInfo;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.bp package. 
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

    private final static QName _OrderCakeResponse_QNAME = new QName("http://www.bp.org", "orderCakeResponse");
    private final static QName _CancelCakeOrderResponse_QNAME = new QName("http://www.bp.org", "cancelCakeOrderResponse");
    private final static QName _OrderPreviewResponse_QNAME = new QName("http://www.bp.org", "orderPreviewResponse");
    private final static QName _OrderIdRequest_QNAME = new QName("http://www.bp.org", "orderIdRequest");
    private final static QName _OrderException_QNAME = new QName("http://www.bp.org", "orderException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.bp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OrderCakeRequest }
     * 
     */
    public OrderCakeRequest createOrderCakeRequest() {
        return new OrderCakeRequest();
    }

    /**
     * Create an instance of {@link CancelCakeOrderRequest }
     * 
     */
    public CancelCakeOrderRequest createCancelCakeOrderRequest() {
        return new CancelCakeOrderRequest();
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
    @XmlElementDecl(namespace = "http://www.bp.org", name = "orderCakeResponse")
    public JAXBElement<OrderInfo> createOrderCakeResponse(OrderInfo value) {
        return new JAXBElement<OrderInfo>(_OrderCakeResponse_QNAME, OrderInfo.class, null, value);
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
    @XmlElementDecl(namespace = "http://www.bp.org", name = "orderPreviewResponse")
    public JAXBElement<OrderInfo> createOrderPreviewResponse(OrderInfo value) {
        return new JAXBElement<OrderInfo>(_OrderPreviewResponse_QNAME, OrderInfo.class, null, value);
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

}
