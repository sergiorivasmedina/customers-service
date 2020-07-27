package com.bootcamp.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTypeCustomerDTO {
    private String idBankAccount;
    private String accountType;
    private String name;
    private String currency;
    private String currencyName;
}