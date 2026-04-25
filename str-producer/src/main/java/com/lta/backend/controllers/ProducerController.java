package com.lta.backend.controllers;

import com.lta.backend.services.StringProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProducerController {

    @Autowired
    private StringProducerService producer;

    @PutMapping("/product/update")
    public String updateStock(@RequestBody String body){
        producer.sendMessage("store-stock", 1, body);
        return "Stock actualizado";
    }

    @PostMapping("/order/create")
    public String createOrder(@RequestBody String body){
        producer.sendMessage("store-orders", 0, body);
        return "Order created";
    }
}