package com.inventory.inventory.controller;

import com.inventory.inventory.dto.InventoryRequest;
import com.inventory.inventory.dto.InventoryResponse;
import com.inventory.inventory.model.InventoryItem;
import com.inventory.inventory.repository.InventoryRepository;
import com.inventory.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    private final InventoryService service;

    public InventoryController(InventoryService service, InventoryRepository inventoryRepository) {
        this.service = service;
        this.inventoryRepository = inventoryRepository;
    }

    // Add new item
    @PostMapping
    public InventoryResponse addItem(
            @RequestBody InventoryRequest request,
            @RequestHeader("X-User") String username
    ) {
        InventoryItem item = service.addItem(request, username);

        InventoryResponse res = new InventoryResponse();
        res.setId(item.getId());
        res.setItemCode(item.getItemCode());
        res.setItemName(item.getItemName());
        res.setQuantity(item.getQuantity());
        res.setPrice(item.getPrice());

        return res;
    }

    // Get all items
    @GetMapping
    public List<InventoryItem> getAll() {
        return service.getAllItems();
    }

    // Get by item code
    @GetMapping("/{code}")
    public InventoryItem getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    // Reduce stock (used by Order Service later)
    @PostMapping("/{code}/reduce")
    public String reduceStock(
            @PathVariable String code,
            @RequestParam int qty
    ) {
    	System.out.println("-->  "+code + qty);
        service.reduceStock(code, qty);
        return "Stock updated";
    }
}

