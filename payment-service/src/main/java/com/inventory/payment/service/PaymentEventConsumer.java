package com.inventory.payment.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.inventory.order.common.OrderCreatedEvent;
import com.inventory.payment.model.Payment;
import com.inventory.payment.repository.PaymentRepository;

@Service
public class PaymentEventConsumer {

    private final PaymentRepository paymentRepository;

    public PaymentEventConsumer(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @KafkaListener(
        topics = "order-created-topic",
        groupId = "payment-group"
    )
    public void consume(OrderCreatedEvent event) {

        System.out.println("🔔 Order event received: " + event.getOrderNumber());

        // Reuse existing Payment entity
        Payment payment = new Payment();
        payment.setOrderId(event.getOrderNumber());
        payment.setAmount(event.getAmount());
        payment.setStatus("SUCCESS");

        paymentRepository.save(payment);

        System.out.println("✅ Payment saved for order: " + event.getOrderNumber());
    }
}

