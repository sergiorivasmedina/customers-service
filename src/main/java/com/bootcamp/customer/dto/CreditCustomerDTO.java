package com.bootcamp.customer.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCustomerDTO {
    private String idCredit;
    private String idCurrency;
    private String currencyName;
    private String creditType;
    private String creditName;
    private Double limit;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date createdAt;
}