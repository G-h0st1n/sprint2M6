package com.lta.backend.controllers;

import com.lta.backend.services.StringProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private StringProducerService producer;

    @PostMapping("product")
    public ResponseEntity<String> addProduct(@RequestBody String product) {
        producer.sendMessage("store-stock", 0, product);
        return ResponseEntity.ok("Product sent to Kafka");
    }

    @PostMapping("/stock/update")
    public ResponseEntity<String> updateStock(@RequestBody String stock) {
        producer.sendMessage("store-stock", 1, stock);
        return ResponseEntity.ok("Stock update sent");
    }

    @PostMapping("/product/delete")
    public ResponseEntity<String> deleteProduct(@RequestBody String product) {
        producer.sendMessage("store-stock", 2, product);
        return ResponseEntity.ok("Delete request sent");
    }
    
    @PostMapping("/order")
    public ResponseEntity<String> newOrder(@RequestBody String order) {
        producer.sendMessage("store-orders", 0, order);
        return ResponseEntity.ok("Order sent");
    }

    @PostMapping("/order/cancel")
    public ResponseEntity<String> cancelOrder(@RequestBody String order) {
        producer.sendMessage("store-orders", 1, order);
        return ResponseEntity.ok("Cancel request sent");
    }

    @PostMapping("/order/custom")
    public ResponseEntity<String> customOrder(@RequestBody String order) {
        producer.sendMessage("store-orders", 2, order);
        return ResponseEntity.ok("Custom order sent");
    }
}