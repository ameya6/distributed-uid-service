package org.duid;


import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@Log4j2
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EntityScan( basePackages = {"org.duid.model"} )
public class DUIDInit {
    public static void main(String[] args) {
        SpringApplication.run(DUIDInit.class, args);
    }
}