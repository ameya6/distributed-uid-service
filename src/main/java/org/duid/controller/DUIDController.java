package org.duid.controller;

import lombok.extern.log4j.Log4j2;
import org.duid.model.DUIDResponse;
import org.duid.service.DUIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
@Log4j2
@RequestMapping("/api/v1/duid")
public class DUIDController {

    @Autowired
    private DUIDService duidService;

    @GetMapping("/generate")
    public ResponseEntity<DUIDResponse> generate() {
        try {
            return ResponseEntity.ok(duidResponse());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse(e));
        }
    }

    private DUIDResponse duidResponse() throws UnknownHostException, ExecutionException, InterruptedException {
        DUIDResponse response = DUIDResponse.builder()
                .duid(duidService.generate())
                .createdAt(LocalDateTime.now()).build();
        log.info("DUID : {}", response.getDuid());
        return response;
    }

    private DUIDResponse errorResponse(Exception e) {
        return DUIDResponse.builder().message(e.getMessage()).build();
    }
}
