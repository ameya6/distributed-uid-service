package org.duid.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="duid_process")
public class DUIDProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "start_time", nullable = false)
    private Long startTime;

    @Column(name = "end_time", nullable = false)
    private Long endTime;

    @Column(name = "process_time", nullable = false)
    private Long processTime;

    @Column(name = "duid", unique = true, nullable = false)
    private Long duid;
}
