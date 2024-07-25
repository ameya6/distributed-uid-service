package org.log.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Enumeration;

@Configuration
@Log4j2
public class DUIDConfig {

    private static final int NODE_ID_BITS = 10;

    private static final long maxNodeId = (1L << NODE_ID_BITS) - 1;

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
}
