package com.inventory.order.common;

import lombok.Data;

@Data
public class OrderCreatedEvent {

    private String orderNumber;
    private String itemCode;
    private int quantity;
    private double amount;
    private String user;

    // getters & setters
}

