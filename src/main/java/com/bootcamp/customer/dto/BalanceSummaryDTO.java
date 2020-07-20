package com.bootcamp.customer.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BalanceSummaryDTO {
    private String idCustomer;
    private String customerName;
    private List<BankAccountDTO> accounts;
    private List<CreditDTO> credits;
}