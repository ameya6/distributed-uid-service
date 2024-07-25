package org.log.conntroller;

import lombok.extern.log4j.Log4j2;
import org.log.model.DUIDResponse;
import org.log.service.DUIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Log4j2
@RequestMapping("/api/v1/duid")
public class DUIDController {

    @Autowired
    private DUIDService duidService;

    @GetMapping("/generate")
    public ResponseEntity<DUIDResponse> generate() {
        return ResponseEntity.ok(duidResponse());
    }

    private DUIDResponse duidResponse() {
        DUIDResponse response = DUIDResponse.builder()
                .duid(duidService.nextId())
                .createdAt(LocalDateTime.now()).build();
        log.info("DUID : " + response.getDuid());
        return response;
    }
}
