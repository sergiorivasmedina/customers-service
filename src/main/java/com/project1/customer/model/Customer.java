package com.project1.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {
    @Id
    private String idCustomer;
    private String name;

    public Customer() {
    }

    public Customer(String idCustomer, String name) {
        this.idCustomer = idCustomer;
        this.name = name;
    }

    public String getIdCustomer() {
        return this.idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
            " idCustomer='" + getIdCustomer() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}