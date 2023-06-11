package com.saubank.saubank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "BankCard")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankCard {
    @Id
    private String id;
    private Integer bankCode;
    private String bankName;
    private Integer binNumber;
    private String cardType;
    private String organization;
    private Short isCommercialCard;
    private Short isSupportInstallment;

}
