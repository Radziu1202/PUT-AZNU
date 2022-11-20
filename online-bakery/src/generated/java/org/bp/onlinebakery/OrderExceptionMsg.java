
package org.bp.onlinebakery;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.3.2
 * 2022-11-20T21:55:13.903+01:00
 * Generated source version: 3.3.2
 */

@WebFault(name = "orderException", targetNamespace = "http://www.bp.org")
public class OrderExceptionMsg extends Exception {

    private org.bp.types.OrderException orderException;

    public OrderExceptionMsg() {
        super();
    }

    public OrderExceptionMsg(String message) {
        super(message);
    }

    public OrderExceptionMsg(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public OrderExceptionMsg(String message, org.bp.types.OrderException orderException) {
        super(message);
        this.orderException = orderException;
    }

    public OrderExceptionMsg(String message, org.bp.types.OrderException orderException, java.lang.Throwable cause) {
        super(message, cause);
        this.orderException = orderException;
    }

    public org.bp.types.OrderException getFaultInfo() {
        return this.orderException;
    }
}
