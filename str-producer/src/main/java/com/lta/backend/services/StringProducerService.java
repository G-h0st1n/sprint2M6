package com.lta.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringProducerService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessage(String topic, int partition, String message){
        kafkaTemplate.send(topic, partition, null, message)
                .whenComplete((result,ex) -> {
                    if(ex != null){
                        log.error("Error al enviar: {}",ex.getMessage());
                    } else {
                        log.info("Mensaje enviado: {}",result.getProducerRecord().value());
                    }
                });
    }
}