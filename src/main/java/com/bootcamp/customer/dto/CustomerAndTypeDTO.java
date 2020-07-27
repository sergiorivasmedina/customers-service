package com.bootcamp.customer.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomerAndTypeDTO {
    private String customerId;
    private String name;
    private String bankId;
    private String bankName;
    private String description;
    private String identityNumber;
    private List<AccountTypeCustomerDTO> accounts;

    public CustomerAndTypeDTO(String customerId, String name, String bankId, String description, String identityNumber) {
        this.customerId = customerId;
        this.name = name;
        this.bankId = bankId;
        this.description = description;
        this.identityNumber = identityNumber;
    }
    public CustomerAndTypeDTO(CustomerAndTypeDTO c, String bankName) {
        this(c.getCustomerId(),c.getName(),c.getBankId(),c.getDescription(),c.getIdentityNumber());
        this.bankName = bankName;
    }
}