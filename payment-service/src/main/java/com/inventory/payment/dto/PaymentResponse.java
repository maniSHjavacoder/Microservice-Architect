
package com.inventory.payment.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private Long paymentId;
    private String orderId;
    private String status;

    // getters & setters
}

