package ru.emiren.protocol.Service.Cleanup.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.emiren.protocol.DTO.Temporal.DocumentHolder;
import ru.emiren.protocol.DTO.Temporal.FileHolder;
import ru.emiren.protocol.Service.Cleanup.DocumentCleanupService;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

@Service
@Slf4j
public class DocumentCleanupServiceImpl implements DocumentCleanupService {
    private final FileHolder fileHolder;


    public DocumentCleanupServiceImpl(FileHolder fileHolder) {
        this.fileHolder = fileHolder;
    }

    @Scheduled(fixedRate = 1800000)
    public void cleanUpOldDocuments(){
        LocalDateTime now = LocalDateTime.now();
        Iterator<Map.Entry<String, DocumentHolder>> iter = fileHolder.getHolder().entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, DocumentHolder> entry = iter.next();
            DocumentHolder documentHolder = entry.getValue();
            if (documentHolder.getCreationTime().plusMinutes(30).isBefore(now)){
                iter.remove();
                log.info("Deleted document with ID: {}", entry.getKey());
            }
        }
    }
}
