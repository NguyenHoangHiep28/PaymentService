package com.example.paymentservice.queue;

import com.example.paymentservice.entity.enums.InventoryStatus;
import com.example.paymentservice.entity.enums.PaymentStatus;
import com.example.paymentservice.event.OrderEvent;
import com.example.paymentservice.service.ITransactionHistoryService;
import lombok.extern.log4j.Log4j2;
import org.omg.IOP.TransactionService;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QueueService {
    private final ITransactionHistoryService transactionService;

    public QueueService(ITransactionHistoryService transactionService) {
        this.transactionService = transactionService;
    }

    public OrderEvent handleCheckOutOrder(OrderEvent orderEvent) {
        log.info(orderEvent.toString());
        boolean checkOutSuccess = transactionService.processCheckOutForOrder(orderEvent);
        if (checkOutSuccess) {
            orderEvent.setPaymentStatus(PaymentStatus.PAID);
            log.info("Order checkout successfully!");
        } else {
            orderEvent.setPaymentStatus(PaymentStatus.FAILED);
            orderEvent.setMessage("User wallet does not enough money!");
            log.info("Check out failed!");
        }
        return orderEvent;
    }

    public OrderEvent handleRefundOrder(OrderEvent orderEvent) {
//        log.info(orderEvent.toString());
//        if (!orderEvent.validationInventory()) {
//            throw new RequestNotValidException("Order is not valid!" + orderEvent.toString());
//        }
        boolean refundSuccess = transactionService.processRefundForOrder(orderEvent);
        if (refundSuccess) {
            log.info("Send notification refund successfully!");
            orderEvent.setPaymentStatus(PaymentStatus.REFUND_SUCCESS);
        } else {
            log.info("Send notification order refund failed!");
        }
        return orderEvent;
    }
}
