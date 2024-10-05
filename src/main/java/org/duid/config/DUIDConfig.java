package org.duid.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.time.utils.LocalDateTimeAdapter;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Enumeration;

@Configuration
@Log4j2
@EnableAsync
public class DUIDConfig {

    private static final int NODE_ID_BITS = 10;
    private static final long maxNodeId = (1L << NODE_ID_BITS) - 1;

    @PostConstruct
    public void info() {
        Runtime runtime = Runtime.getRuntime();
        log.info("Memory Total : {}, Max : {}, Free : {}", runtime.totalMemory(), runtime.maxMemory(), runtime.freeMemory());
    }

    @Bean
    @Qualifier("nodeId")
    public long createNodeId() {
        long nodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for(byte macPort: mac) {
                        sb.append(String.format("%02X", macPort));
                    }
                }
            }
            nodeId = sb.toString().hashCode();
        } catch (Exception ex) {
            nodeId = (new SecureRandom().nextInt());
        }
        nodeId = nodeId & maxNodeId;
        log.info("Node id : " + nodeId);
        return nodeId;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

}
