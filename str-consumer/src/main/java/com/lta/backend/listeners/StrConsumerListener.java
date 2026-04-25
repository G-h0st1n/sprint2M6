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

    // =========================
    // STORE STOCK
    // =========================
    @KafkaListener(topics = "store-stock", groupId = "store-group")
    public void handleStock(String message,
                            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        log.info("Stock event received: {} from partition {}", message, partition);

        switch (partition) {
            case 0:
                log.info("ADD PRODUCT -> {}", message);
                break;

            case 1:
                log.info("UPDATE STOCK -> {}", message);
                break;

            case 2:
                log.info("DELETE PRODUCT -> {}", message);
                break;

            default:
                log.warn("Unknown stock partition: {}", partition);
        }

        log.info("Stock event processed (async, no direct DB write)");
    }

    // =========================
    // STORE ORDERS
    // =========================
    @KafkaListener(topics = "store-orders", groupId = "store-group")
    public void handleOrders(String message,
                             @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        log.info("Order event received: {} from partition {}", message, partition);

        String projectionMessage = "";

        switch (partition) {
            case 0:
                log.info("NEW ORDER -> {}", message);
                projectionMessage = "Order created: " + message;
                break;

            case 1:
                log.info("CANCEL ORDER -> {}", message);
                projectionMessage = "Order canceled: " + message;
                break;

            case 2:
                log.info("CUSTOM ORDER -> {}", message);
                projectionMessage = "Custom order: " + message;
                break;

            default:
                log.warn("Unknown order partition: {}", partition);
        }

        // 🔥 EVENT-DRIVEN: enviamos a proyecciones
        kafkaTemplate.send("store-projections", projectionMessage);

        log.info("Order event processed and forwarded to projections");
    }

    // =========================
    // STORE VIEWS (PROJECTIONS)
    // =========================
    @KafkaListener(topics = "store-projections", groupId = "store-group")
    public void handleProjections(String message,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        log.info("Projection event received: {} from partition {}", message, partition);

        switch (partition) {
            case 0:
                log.info("UPDATE PRODUCT CATALOG VIEW");
                break;

            case 1:
                log.info("UPDATE ORDER STATUS VIEW");
                break;

            default:
                log.info("General projection update");
        }

        log.info("Projection processed (read model updated)");
    }
}