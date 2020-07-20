package com.bootcamp.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditDTO {
    private String idCredit;
    private Double availableAmount;
    private String creditTypeName;
    private String currencyName;
    private Double consumedAmount;
    private Double limit;
}