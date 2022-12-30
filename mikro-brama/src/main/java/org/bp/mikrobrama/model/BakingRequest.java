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

package org.bp.mikrobrama.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * BakingRequest
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-12-23T15:31:47.012026+01:00[Europe/Warsaw]")
public class BakingRequest {
  @JsonProperty("person")
  private Person person = null;

  @JsonProperty("bread")
  private Bread bread = null;

  @JsonProperty("cake")
  private Cake cake = null;

  @JsonProperty("paymentCard")
  private PaymentCard paymentCard = null;

  @JsonProperty("deliveryPlace")
  private DeliveryPlace deliveryPlace = null;

  public BakingRequest person(Person person) {
    this.person = person;
    return this;
  }

   /**
   * Get person
   * @return person
  **/
  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public BakingRequest bread(Bread bread) {
    this.bread = bread;
    return this;
  }

   /**
   * Get bread
   * @return bread
  **/
  public Bread getBread() {
    return bread;
  }

  public void setBread(Bread bread) {
    this.bread = bread;
  }

  public BakingRequest cake(Cake cake) {
    this.cake = cake;
    return this;
  }

   /**
   * Get cake
   * @return cake
  **/
  public Cake getCake() {
    return cake;
  }

  public void setCake(Cake cake) {
    this.cake = cake;
  }

  public BakingRequest paymentCard(PaymentCard paymentCard) {
    this.paymentCard = paymentCard;
    return this;
  }

   /**
   * Get paymentCard
   * @return paymentCard
  **/
  public PaymentCard getPaymentCard() {
    return paymentCard;
  }

  public void setPaymentCard(PaymentCard paymentCard) {
    this.paymentCard = paymentCard;
  }

  public BakingRequest deliveryPlace(DeliveryPlace deliveryPlace) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BakingRequest bakingRequest = (BakingRequest) o;
    return Objects.equals(this.person, bakingRequest.person) &&
        Objects.equals(this.bread, bakingRequest.bread) &&
        Objects.equals(this.cake, bakingRequest.cake) &&
        Objects.equals(this.paymentCard, bakingRequest.paymentCard) &&
        Objects.equals(this.deliveryPlace, bakingRequest.deliveryPlace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(person, bread, cake, paymentCard, deliveryPlace);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BakingRequest {\n");
    
    sb.append("    person: ").append(toIndentedString(person)).append("\n");
    sb.append("    bread: ").append(toIndentedString(bread)).append("\n");
    sb.append("    cake: ").append(toIndentedString(cake)).append("\n");
    sb.append("    paymentCard: ").append(toIndentedString(paymentCard)).append("\n");
    sb.append("    deliveryPlace: ").append(toIndentedString(deliveryPlace)).append("\n");
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