package com.inventory.payment.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private double amount;
    private String paymentMode;   // CASH, CARD, UPI
    private String status;        // SUCCESS, FAILED, PENDING

    private String paidBy;        // username from gateway
    private LocalDateTime paidAt;

    @PrePersist
    public void onPay() {
        this.paidAt = LocalDateTime.now();
    }

    // getters & setters
}

