package com.project1.customer.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "personal")
public class Personal extends Customer {

    private String dni;

    public Personal() {
    }

    public Personal(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "{" +
            " dni='" + getDni() + "'" +
            "}";
    }
}