package com.bootcamp.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "corporate")
public class Corporate {
    @Id
    private String idCorporate;
    private String description;
    private String ruc;

    public Corporate(String description, String ruc) {
        this.description = description;
        this.ruc = ruc;
    }
}