package org.duid.service;

import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.time.model.ProcessTimeLogDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class TimeLogPublisher {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private Gson gson;

    @Async
    public CompletableFuture<SendResult<String, Object>> save(ProcessTimeLogDTO processTimeLog) {
        try {
            String timeLogJson = gson.toJson(processTimeLog);
            log.info(timeLogJson);
            return kafkaTemplate.send("time-log-topic", UUID.randomUUID().toString(), timeLogJson);
        } catch (Exception e) {
            log.error("Exception while saving to timescale: {}", e.getMessage(), e);
            throw e;
        }
    }
}
