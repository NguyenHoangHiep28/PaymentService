package com.example.paymentservice.queue;

import com.example.paymentservice.entity.enums.InventoryStatus;
import com.example.paymentservice.entity.enums.PaymentStatus;
import com.example.paymentservice.event.OrderEvent;
import com.example.paymentservice.exception.RequestNotValidException;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.paymentservice.queue.Config.*;

@Component
@Log4j2
public class ReceiveMessage {
    private final QueueService queueService;
    private final AmqpTemplate rabbitTemplate;
    public ReceiveMessage(QueueService queueService, AmqpTemplate rabbitTemplate) {
        this.queueService = queueService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = {QUEUE_PAY, DIRECT_QUEUE_PAY})
    public void handleOrder(OrderEvent orderEvent) {
        if (!orderEvent.validationInventory()) {
            throw new RequestNotValidException("Order is not valid!" + orderEvent.toString());
        }
        OrderEvent returnEvent = orderEvent;
        if (orderEvent.getPaymentStatus().equals(PaymentStatus.WAITING_FOR_PROCESS)) {
            returnEvent = queueService.handleCheckOutOrder(orderEvent);
            log.info(returnEvent);
        } else if (orderEvent.getPaymentStatus().equals(PaymentStatus.REFUND)) {
            System.out.println("vao refund !");
            returnEvent = queueService.handleRefundOrder(orderEvent);
            log.info(returnEvent);
        }
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY_ORDER, returnEvent);
    }
}
