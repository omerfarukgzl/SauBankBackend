package com.saubank.saubank.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",strategy="org.hibernate.id.UUIDGenerator")
    private String id;
    private String cardNumber;
    private Integer binNumber;
    private String cardHolderName;
    private String cardCvv;
    private String cardExpireDate;
    private String cardType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable=false)// foreign key
    private User userId;

}
