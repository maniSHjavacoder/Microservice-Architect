
package com.inventory.inventory.dto;

import lombok.Data;

@Data 

public class InventoryResponse {
	
    private Long id;
    private String itemCode;
    private String itemName;
    private int quantity;
    private double price;

    // getters & setters
}

