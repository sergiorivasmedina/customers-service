package com.project1.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {
    @Id
    private Integer idCustomer;
    private String name;
    private String lastname;

    public Customer() {
    }

    public Customer(Integer idCustomer, String name, String lastname) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.lastname = lastname;
    }

    public Integer getIdCustomer() {
        return this.idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "{" +
            " idCustomer='" + getIdCustomer() + "'" +
            ", name='" + getName() + "'" +
            ", lastname='" + getLastname() + "'" +
            "}";
    }
}