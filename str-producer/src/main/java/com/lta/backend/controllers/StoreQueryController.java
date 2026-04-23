package com.lta.backend.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lta.backend.services.OrderProcessor;
import com.lta.backend.services.StockService;

@RestController
@RequestMapping("/store")
public class StoreQueryController {

     @Autowired
    private StockService stockService;

    @Autowired
    private OrderProcessor orderProcessor;

    @GetMapping("/inventory")
    public Map<String, Integer> inventory() {
        return stockService.getInventory();
    }

    @GetMapping("/orders")
    public Map<String, String> orders() {
        return orderProcessor.getOrders();
    }
}
