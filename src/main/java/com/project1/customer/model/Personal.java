package com.project1.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "personal")
public class Personal {

    @Id
    private String idPersonal;
    private String dni;

    public Personal() {
    }

    public Personal(String dni) {
        this.dni = dni;
    }

    public Personal(String idPersonal, String dni) {
        this.idPersonal = idPersonal;
        this.dni = dni;
    }

    public String getIdPersonal() {
        return this.idPersonal;
    }

    public void setIdPersonal(String idPersonal) {
        this.idPersonal = idPersonal;
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
            " idPersonal='" + getIdPersonal() + "'" +
            ", dni='" + getDni() + "'" +
            "}";
    }
}