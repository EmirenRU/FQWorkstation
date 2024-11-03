package ru.emiren.infosystemdepartment.Service.Cleanup.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Model.Temporal.DocumentHolder;
import ru.emiren.infosystemdepartment.Model.Temporal.FileHolder;
import ru.emiren.infosystemdepartment.Service.Cleanup.DocumentCleanupService;

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
