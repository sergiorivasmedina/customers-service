package com.project1.customer.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "business")
public class Business extends Customer {
    
    private String ruc;

    public Business() {
    }

    public Business(String ruc) {
        this.ruc = ruc;
    }

    public String getRuc() {
        return this.ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    @Override
    public String toString() {
        return "{" +
            " ruc='" + getRuc() + "'" +
            "}";
    }
}