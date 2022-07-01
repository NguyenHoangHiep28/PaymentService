package com.example.paymentservice.entity;

import com.example.paymentservice.entity.enums.PaymentType;
import com.example.paymentservice.entity.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_histories")
@Entity
@Builder
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // For Online Checkout
    // senderId = userId for cash checkout
    private String senderId;
    private String receiverId;

    private TransactionType transactionType;
    private PaymentType paymentType;
    private String orderId;
    private BigDecimal amount;
    private Boolean success;
    private String description;

}
