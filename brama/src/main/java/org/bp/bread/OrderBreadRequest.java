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

package org.bp.bread;

import java.util.Objects;
import java.util.Arrays;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import org.bp.bread.Bread;
import org.bp.bread.DeliveryPlace;
import org.bp.bread.Person;

/**
 * OrderBreadRequest
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-12-15T21:28:15.586613400+01:00[Europe/Warsaw]")
public class OrderBreadRequest {
    @SerializedName("person")
    private Person person = null;

    @SerializedName("deliveryPlace")
    private DeliveryPlace deliveryPlace = null;

    @SerializedName("bread")
    private Bread bread = null;

    public OrderBreadRequest person(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Get person
     *
     * @return person
     **/
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public OrderBreadRequest deliveryPlace(DeliveryPlace deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
        return this;
    }

    /**
     * Get deliveryPlace
     *
     * @return deliveryPlace
     **/
    public DeliveryPlace getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(DeliveryPlace deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public OrderBreadRequest bread(Bread bread) {
        this.bread = bread;
        return this;
    }

    /**
     * Get bread
     *
     * @return bread
     **/
    public Bread getBread() {
        return bread;
    }

    public void setBread(Bread bread) {
        this.bread = bread;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderBreadRequest orderBreadRequest = (OrderBreadRequest) o;
        return Objects.equals(this.person, orderBreadRequest.person) &&
                Objects.equals(this.deliveryPlace, orderBreadRequest.deliveryPlace) &&
                Objects.equals(this.bread, orderBreadRequest.bread);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, deliveryPlace, bread);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OrderBreadRequest {\n");

        sb.append("    person: ").append(toIndentedString(person)).append("\n");
        sb.append("    deliveryPlace: ").append(toIndentedString(deliveryPlace)).append("\n");
        sb.append("    bread: ").append(toIndentedString(bread)).append("\n");
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
