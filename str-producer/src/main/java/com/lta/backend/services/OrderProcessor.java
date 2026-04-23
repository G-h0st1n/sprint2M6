package com.lta.backend.services;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProcessor {

    private final Map<String, String> orders = new HashMap<>();

    public void newOrder(String order) {
        orders.put(UUID.randomUUID().toString(), order);
        log.info("Order created: {}", order);
    }

    public void cancelOrder(String orderId) {
        orders.remove(orderId);
        log.info("Order cancelled: {}", orderId);
    }

    public void customOrder(String order) {
        log.info("Custom order received: {}", order);
    }

    public Map<String, String> getOrders() {
        return orders;
    }
}