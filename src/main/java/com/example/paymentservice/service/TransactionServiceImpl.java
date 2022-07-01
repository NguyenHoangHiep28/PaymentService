package com.example.paymentservice.service;

import com.example.paymentservice.entity.TransactionHistory;
import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.entity.enums.PaymentType;
import com.example.paymentservice.entity.enums.TransactionType;
import com.example.paymentservice.event.OrderEvent;
import com.example.paymentservice.repository.TransactionHistoryRepository;
import com.example.paymentservice.repository.WalletRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements ITransactionHistoryService {
    private final String storeWalletId = "ec082bbe-f7e9-46d1-b9c9-9ed0c568bff2";
    private final WalletRepository walletRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionServiceImpl(WalletRepository walletRepository, TransactionHistoryRepository transactionHistoryRepository) {
        this.walletRepository = walletRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Override
    public boolean processCheckOutForOrder(OrderEvent orderEvent) {
        Optional<Wallet> userWallet = walletRepository.findById(orderEvent.getUserId());
        boolean success = false;
        if (userWallet.isPresent()) {
            Wallet wallet = userWallet.get();
            String description = "Pay for order #" + orderEvent.getOrderId();
            TransactionHistory transactionHistory = TransactionHistory.builder()
                    .amount(orderEvent.getTotalPrice())
                    .description(description)
                    .paymentType(PaymentType.ONLINE_PAYMENT)
                    .orderId(orderEvent.getOrderId())
                    .senderId(wallet.getUserId())
                    .receiverId(storeWalletId)
                    .transactionType(TransactionType.PAY_FOR_ORDER)
                    .build();
            if (wallet.getBalance().compareTo(orderEvent.getTotalPrice()) >= 0) {
                wallet.setBalance(wallet.getBalance().subtract(orderEvent.getTotalPrice()));
                transactionHistory.setSuccess(true);
                walletRepository.save(wallet);
                success = true;
            } else {
                transactionHistory.setSuccess(false);
            }
            transactionHistoryRepository.save(transactionHistory);
            return success;
        } else {
            return false;
        }
    }

    @Override
    public boolean processRefundForOrder(OrderEvent orderEvent) {
        TransactionHistory history = transactionHistoryRepository.findByOrderIdAndTransactionType(orderEvent.getOrderId(), TransactionType.PAY_FOR_ORDER);
        if (history != null) {
            Optional<Wallet> userWalletOptional = walletRepository.findById(orderEvent.getUserId());
            if (userWalletOptional.isPresent()) {
                Wallet userWallet = userWalletOptional.get();
                userWallet.setBalance(userWallet.getBalance().add(history.getAmount()));
                String description = "Refund for user" + userWallet.getName() + "from order #" + orderEvent.getOrderId();
                TransactionHistory transactionHistory = TransactionHistory.builder()
                        .amount(orderEvent.getTotalPrice())
                        .description(description)
                        .paymentType(PaymentType.ONLINE_PAYMENT)
                        .orderId(orderEvent.getOrderId())
                        .senderId(storeWalletId)
                        .receiverId(userWallet.getUserId())
                        .transactionType(TransactionType.REFUND)
                        .success(true)
                        .build();
                transactionHistoryRepository.save(transactionHistory);
                walletRepository.save(userWallet);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
