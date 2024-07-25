package org.log.model;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DUIDResponse {
    private Long duid;
    private LocalDateTime createdAt;
}
