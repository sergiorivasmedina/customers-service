package com.project1.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "business")
public class Business {
    
    @Id
    private String idBusiness;
    private String ruc;

    public Business() {
    }

    public Business(String idBusiness, String ruc) {
        this.idBusiness = idBusiness;
        this.ruc = ruc;
    }

    public String getIdBusiness() {
        return this.idBusiness;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
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
            " idBusiness='" + getIdBusiness() + "'" +
            ", ruc='" + getRuc() + "'" +
            "}";
    }
}