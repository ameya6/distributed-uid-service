package org.duid.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class DUIDService {

    private static final int UNUSED_BITS = 1;
    private static final int EPOCH_BITS = 41;
    private static final int SEQUENCE_BITS = 12;
    private static final long maxSequence = (1L << SEQUENCE_BITS) - 1;
    // Custom Epoch (January 1, 2015 Midnight UTC = 2015-01-01T00:00:00Z)
    private static final long DEFAULT_CUSTOM_EPOCH = 1420070400000L;
    private static final int NODE_ID_BITS = 10;
    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;
    @Autowired
    public long nodeId;

    public synchronized long nextId() {
        long currentTimestamp = timestamp();

        if(currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Invalid System Clock!");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
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
