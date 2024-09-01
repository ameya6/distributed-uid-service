package org.duid.service;

import lombok.extern.log4j.Log4j2;
import org.duid.dao.DUIDDao;
import org.duid.model.DUIDProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class TimeScaleService {

    @Autowired
    private DUIDDao duidDao;

    @Async
    public void save(DUIDProcess duidProcess) {
        try {
            CompletableFuture<DUIDProcess> timeScaleSave = CompletableFuture.completedFuture(duidDao.save(duidProcess));
            CompletableFuture.allOf(timeScaleSave).join();
        } catch (Exception e) {
            log.error("Exception while saving to timescale: {}", e.getMessage(), e);
        }
    }
}
