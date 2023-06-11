package com.saubank.saubank.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankRequest {
    private BigDecimal amount;
    private String bankCode;
    private String cardNumber;
    private String token;
}
