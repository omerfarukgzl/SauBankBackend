package com.saubank.saubank.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Bank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bank {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",strategy="org.hibernate.id.UUIDGenerator")
    private String id;
    private String bankName;
    private Integer bankCode;

    @OneToMany(mappedBy = "bank",fetch = FetchType.EAGER, cascade = CascadeType.ALL) // ==> mappedBy="account" : account nesnesi Transaction nesnesindeki account nesnesine karşılık gelir.
    private Set<User> users  = new HashSet<>() ;

/*    @OneToMany(mappedBy = "bank",fetch = FetchType.EAGER, cascade = CascadeType.ALL) // ==> mappedBy="account" : account nesnesi Transaction nesnesindeki account nesnesine karşılık gelir.
    private Set<Card> cards  = new HashSet<>() ;*/










}
