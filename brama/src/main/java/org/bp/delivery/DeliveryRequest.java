/*
 * OpenAPI definition
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.bp.delivery;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.bp.DeliveryPlace;
/**
 * DeliveryRequest
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-12-23T14:54:59.357066+01:00[Europe/Warsaw]")
public class DeliveryRequest {
  @SerializedName("deliveryPlace")
  private DeliveryPlace deliveryPlace = null;

  @SerializedName("orderId")
  private String orderId = null;

  public DeliveryRequest deliveryPlace(DeliveryPlace deliveryPlace) {
    this.deliveryPlace = deliveryPlace;
    return this;
  }

   /**
   * Get deliveryPlace
   * @return deliveryPlace
  **/
  public DeliveryPlace getDeliveryPlace() {
    return deliveryPlace;
  }

  public void setDeliveryPlace(DeliveryPlace deliveryPlace) {
    this.deliveryPlace = deliveryPlace;
  }

  public DeliveryRequest orderId(String orderId) {
    this.orderId = orderId;
    return this;
  }

   /**
   * Get orderId
   * @return orderId
  **/
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeliveryRequest deliveryRequest = (DeliveryRequest) o;
    return Objects.equals(this.deliveryPlace, deliveryRequest.deliveryPlace) &&
        Objects.equals(this.orderId, deliveryRequest.orderId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deliveryPlace, orderId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeliveryRequest {\n");
    
    sb.append("    deliveryPlace: ").append(toIndentedString(deliveryPlace)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
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