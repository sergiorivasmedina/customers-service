package com.project1.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {
    @Id
    private String idCustomer;
    private String name;
    private int type;
    private String objectId;

    public Customer() {
    }

    public Customer(String idCustomer, String name, int type, String objectId) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.type = type;
        this.objectId = objectId;
    }

    public Customer(String name, int type, String objectId) {
        this.name = name;
        this.type = type;
        this.objectId = objectId;
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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "{" +
            " idCustomer='" + getIdCustomer() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", objectId='" + getObjectId() + "'" +
            "}";
    }
}