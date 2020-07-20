package com.bootcamp.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankAccountDTO {
    private String idBankAccount;
    private Double availableBalance;
    private String accountTypeName;
    private String currencyName;
}