package ru.emiren.protocol.DTO.Temporal;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class DocumentHolder {
    private final byte[] document;
    private final LocalDateTime creationTime;

    DocumentHolder(byte[] document) {
        this.document = document;
        this.creationTime = LocalDateTime.now();
    }
}
