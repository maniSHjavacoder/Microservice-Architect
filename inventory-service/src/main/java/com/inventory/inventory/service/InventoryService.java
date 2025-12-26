
package com.inventory.inventory.service;

import com.inventory.inventory.dto.InventoryRequest;
import com.inventory.inventory.model.InventoryItem;
import com.inventory.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository repo;

    public InventoryService(InventoryRepository repo) {
        this.repo = repo;
    }

    public InventoryItem addItem(InventoryRequest req, String username) {

        InventoryItem item = new InventoryItem();
        item.setItemCode(req.getItemCode());
        item.setItemName(req.getItemName());
        item.setQuantity(req.getQuantity());
        item.setPrice(req.getPrice());
        item.setCreatedBy(username);

        return repo.save(item);
    }

    public List<InventoryItem> getAllItems() {
        return repo.findAll();
    }

    public InventoryItem getByCode(String itemCode) {
        return repo.findByItemCode(itemCode)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public void reduceStock(String itemCode, int qty) {
        InventoryItem item = getByCode(itemCode);

        if (item.getQuantity() < qty) {
            throw new RuntimeException("Insufficient stock");
        }

        item.setQuantity(item.getQuantity() - qty);
        repo.save(item);
    }
}

