/*
 * online bakery microservice
 * Micro service to online bakery
 *
 * OpenAPI spec version: 1.0.0
 * Contact: supportm@bp.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.bp.types;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.bp.types.DeliveryResponse;
import org.bp.types.OrderBreadResponse;
import org.bp.types.OrderInfo;
/**
 * BakingRequestResponse
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-12-29T17:11:20.818038200+01:00[Europe/Warsaw]")
public class BakingRequestResponse {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("orderBreadResponse")
  private OrderBreadResponse orderBreadResponse = null;

  @JsonProperty("orderCakeResponse")
  private OrderInfo orderCakeResponse = null;

  @JsonProperty("orderDeliveryResponse")
  private DeliveryResponse orderDeliveryResponse = null;

  public BakingRequestResponse id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BakingRequestResponse orderBreadResponse(OrderBreadResponse orderBreadResponse) {
    this.orderBreadResponse = orderBreadResponse;
    return this;
  }

   /**
   * Get orderBreadResponse
   * @return orderBreadResponse
  **/
  public OrderBreadResponse getOrderBreadResponse() {
    return orderBreadResponse;
  }

  public void setOrderBreadResponse(OrderBreadResponse orderBreadResponse) {
    this.orderBreadResponse = orderBreadResponse;
  }

  public BakingRequestResponse orderCakeResponse(OrderInfo orderCakeResponse) {
    this.orderCakeResponse = orderCakeResponse;
    return this;
  }

   /**
   * Get orderCakeResponse
   * @return orderCakeResponse
  **/
  public OrderInfo getOrderCakeResponse() {
    return orderCakeResponse;
  }

  public void setOrderCakeResponse(OrderInfo orderCakeResponse) {
    this.orderCakeResponse = orderCakeResponse;
  }

  public BakingRequestResponse orderDeliveryResponse(DeliveryResponse orderDeliveryResponse) {
    this.orderDeliveryResponse = orderDeliveryResponse;
    return this;
  }

   /**
   * Get orderDeliveryResponse
   * @return orderDeliveryResponse
  **/
  public DeliveryResponse getOrderDeliveryResponse() {
    return orderDeliveryResponse;
  }

  public void setOrderDeliveryResponse(DeliveryResponse orderDeliveryResponse) {
    this.orderDeliveryResponse = orderDeliveryResponse;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BakingRequestResponse bakingRequestResponse = (BakingRequestResponse) o;
    return Objects.equals(this.id, bakingRequestResponse.id) &&
        Objects.equals(this.orderBreadResponse, bakingRequestResponse.orderBreadResponse) &&
        Objects.equals(this.orderCakeResponse, bakingRequestResponse.orderCakeResponse) &&
        Objects.equals(this.orderDeliveryResponse, bakingRequestResponse.orderDeliveryResponse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderBreadResponse, orderCakeResponse, orderDeliveryResponse);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BakingRequestResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    orderBreadResponse: ").append(toIndentedString(orderBreadResponse)).append("\n");
    sb.append("    orderCakeResponse: ").append(toIndentedString(orderCakeResponse)).append("\n");
    sb.append("    orderDeliveryResponse: ").append(toIndentedString(orderDeliveryResponse)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
