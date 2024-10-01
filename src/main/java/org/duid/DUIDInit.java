package org.duid;


import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Log4j2
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DUIDInit {
    public static void main(String[] args) throws UnknownHostException {
        System.setProperty("hostName", InetAddress.getLocalHost().getHostName());
        System.setProperty("hostAddress", InetAddress.getLocalHost().getHostAddress());
        SpringApplication.run(DUIDInit.class, args);
    }
}