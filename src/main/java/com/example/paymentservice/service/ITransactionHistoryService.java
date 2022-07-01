package com.example.paymentservice.service;

import com.example.paymentservice.event.OrderEvent;

public interface ITransactionHistoryService {
    boolean processCheckOutForOrder(OrderEvent orderEvent);
    boolean processRefundForOrder(OrderEvent orderEvent);
}
