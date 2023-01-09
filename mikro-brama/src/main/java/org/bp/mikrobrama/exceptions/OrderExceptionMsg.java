
package org.bp.mikrobrama.exceptions;


import org.bp.types.OrderException;


/**
 * This class was generated by Apache CXF 3.3.2
 * 2022-12-21T21:14:39.604+01:00
 * Generated source version: 3.3.2
 */

public class OrderExceptionMsg extends Exception {

    private OrderException orderException;

    public OrderExceptionMsg() {
        super();
    }

    public OrderExceptionMsg(String message) {
        super(message);
    }

    public OrderExceptionMsg(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public OrderExceptionMsg(String message, OrderException orderException) {
        super(message);
        this.orderException = orderException;
    }

    public OrderExceptionMsg(String message, OrderException orderException, java.lang.Throwable cause) {
        super(message, cause);
        this.orderException = orderException;
    }

    public OrderException getFaultInfo() {
        return this.orderException;
    }
}