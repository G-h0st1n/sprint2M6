package com.lta.backend.services;

import java.util.HashMap;
import java.util.Map;

public class StockService {
    private final Map<String, Integer> inventory = new HashMap<>();

    public void addProduct(String json) {
        inventory.put(json, inventory.getOrDefault(json, 0) + 1);
    }

    public void updateStock(String json) {
        inventory.put(json, inventory.getOrDefault(json, 0));
    }

    public void deleteProduct(String json) {
        inventory.remove(json);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

}
