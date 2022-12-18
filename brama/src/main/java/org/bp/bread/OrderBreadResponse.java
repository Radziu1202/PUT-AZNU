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
import java.math.BigDecimal;

import org.bp.bread.Bread;
import org.bp.bread.Person;

/**
 * OrderBreadResponse
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-12-15T21:28:15.586613400+01:00[Europe/Warsaw]")
public class OrderBreadResponse {
    @SerializedName("id")
    private String id = null;

    @SerializedName("person")
    private Person person = null;

    @SerializedName("cost")
    private BigDecimal cost = null;

    @SerializedName("bread")
    private Bread bread = null;

    public OrderBreadResponse id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderBreadResponse person(Person person) {
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

    public OrderBreadResponse cost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    /**
     * Get cost
     *
     * @return cost
     **/
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public OrderBreadResponse bread(Bread bread) {
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
        OrderBreadResponse orderBreadResponse = (OrderBreadResponse) o;
        return Objects.equals(this.id, orderBreadResponse.id) &&
                Objects.equals(this.person, orderBreadResponse.person) &&
                Objects.equals(this.cost, orderBreadResponse.cost) &&
                Objects.equals(this.bread, orderBreadResponse.bread);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, cost, bread);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OrderBreadResponse {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    person: ").append(toIndentedString(person)).append("\n");
        sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
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
