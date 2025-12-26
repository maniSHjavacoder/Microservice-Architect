
package com.inventory.payment.service;

import com.inventory.payment.dto.PaymentRequest;
import com.inventory.payment.model.Payment;
import com.inventory.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository repo;

    public PaymentService(PaymentRepository repo) {
        this.repo = repo;
    }

    public Payment makePayment(PaymentRequest req, String username) {

        Payment payment = new Payment();
        payment.setOrderId(req.getOrderId());
        payment.setAmount(req.getAmount());
        payment.setPaymentMode(req.getPaymentMode());

        // for now assume success
        payment.setStatus("SUCCESS");
        payment.setPaidBy(username);

        return repo.save(payment);
    }
}

