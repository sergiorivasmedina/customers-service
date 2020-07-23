package com.bootcamp.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vip")
public class Vip {
    @Id
    private String idVip;
    private String description;
    private String dni;

    public Vip(String description, String dni) {
        this.description = description;
        this.dni = dni;
    }
}