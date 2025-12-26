
package com.inventory.payment.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private double amount;
    private String paymentMode;

    // getters & setters
}

