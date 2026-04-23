package com.lta.backend.listeners;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class StrConsumerListener {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // STOCK TOPIC (no state changes)
    @KafkaListener(topics = "store-stock", groupId = "store-group")
    public void handleStock(String message,
                            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        log.info("Stock event received: {} from partition {}", message, partition);

        log.info("Processing stock event (NO STATE CHANGE in consumer)");
    }

    // ORDERS TOPIC (no state changes)
    @KafkaListener(topics = "store-orders", groupId = "store-group")
    public void handleOrders(String message,
                             @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        log.info("Order event received: {} from partition {}", message, partition);

        log.info("Processing order event (NO STATE CHANGE in consumer)");

        // optional: forward to projection topic
        kafkaTemplate.send("store-projections",
                "Order processed: " + message);
    }
}