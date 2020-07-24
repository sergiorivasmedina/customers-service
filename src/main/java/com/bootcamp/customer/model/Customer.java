package com.bootcamp.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer")
public class Customer {
    @Id
    private String idCustomer;
    private String name;
    private int type;
    private String objectId;
    private String identityNumber;
    private String bankId;

    public Customer(String name, int type, String objectId, String id,String bankId) {
        this.name = name;
        this.type = type;
        this.objectId = objectId;
        this.identityNumber = id;
        this.bankId = bankId;
    }
}