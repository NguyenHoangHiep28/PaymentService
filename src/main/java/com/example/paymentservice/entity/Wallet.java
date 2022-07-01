package com.example.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallets")
@Entity
public class Wallet {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String name;
    private BigDecimal balance;
}
