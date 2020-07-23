package com.bootcamp.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pyme")
public class Pyme {
    @Id
    private String idPyme;
    private String description;
    private String ruc;

    public Pyme(String description, String ruc) {
        this.description = description;
        this.ruc = ruc;
    }
}