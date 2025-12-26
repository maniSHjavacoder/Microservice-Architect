package com.inventory.payment.controller;

import com.inventory.payment.dto.PaymentRequest;
import com.inventory.payment.dto.PaymentResponse;
import com.inventory.payment.model.Payment;
import com.inventory.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public PaymentResponse pay(
            @RequestBody PaymentRequest request,
            @RequestHeader("X-User") String username
    ) {
        Payment payment = service.makePayment(request, username);

        PaymentResponse res = new PaymentResponse();
        res.setPaymentId(payment.getId());
        res.setOrderId(payment.getOrderId());
        res.setStatus(payment.getStatus());

        return res;
    }
}

