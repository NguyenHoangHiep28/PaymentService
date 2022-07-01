package com.example.paymentservice.repository;

import com.example.paymentservice.entity.TransactionHistory;
import com.example.paymentservice.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
    TransactionHistory findByOrderIdAndTransactionType(String orderId, TransactionType transactionType);
}
