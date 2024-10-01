package org.duid.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.time.model.ProcessTimeLogDTO;
import org.time.model.TimeLog;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@Log4j2
public class DUIDService {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final int EPOCH_BITS = 41;
    private static final int SEQUENCE_BITS = 12;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;
    private static final long DEFAULT_CUSTOM_EPOCH = 1420070400000L;
    private static final int NODE_ID_BITS = 10;
    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;

    @Autowired
    public long nodeId;

    @Autowired
    private TimeLogPublisher timeLogPublisher;

    @PostConstruct
    private void init() {
        log.info(toString());
    }

    public long generate() {
        long startTimeNs = System.nanoTime();
        LocalDateTime startTime = LocalDateTime.now();
        Long duid = nextId();
        long endTimeNs = System.nanoTime();
        LocalDateTime endTime = LocalDateTime.now();
        timeLogPublisher.save(publishTimeLogEntry(TimeLog.of(startTimeNs, startTime, endTimeNs , endTime)));
        return duid;
    }

    private ProcessTimeLogDTO publishTimeLogEntry(TimeLog timeLog) {
        return ProcessTimeLogDTO.builder()
                .startTimeNs(timeLog.startTimeNs())
                .endTimeNs(timeLog.endTimeNs())
                .processTime((double)(timeLog.endTimeNs() - timeLog.startTimeNs()) / 1_000_000)
                .startTime(timeLog.startTime())
                .endTime(timeLog.endTime())
                .hostName(System.getProperty("hostName"))
                .ipAddress(System.getProperty("hostAddress"))
                .serviceName(applicationName)
                .build();
    }

    private synchronized long nextId() {
        long currentTimestamp = timestamp();

        if(currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Invalid System Clock!");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if(sequence == 0) {
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        long id = currentTimestamp << (NODE_ID_BITS + SEQUENCE_BITS)
                | (nodeId << SEQUENCE_BITS)
                | sequence;

        return id;
    }

    private long timestamp() {
        return Instant.now().toEpochMilli() - DEFAULT_CUSTOM_EPOCH;
    }

    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = timestamp();
        }
        return currentTimestamp;
    }

    @Override
    public String toString() {
        return "Snowflake Settings [EPOCH_BITS=" + EPOCH_BITS + ", NODE_ID_BITS=" + NODE_ID_BITS
                + ", SEQUENCE_BITS=" + SEQUENCE_BITS + ", CUSTOM_EPOCH=" + DEFAULT_CUSTOM_EPOCH
                + ", NodeId=" + nodeId + "]";
    }
}
