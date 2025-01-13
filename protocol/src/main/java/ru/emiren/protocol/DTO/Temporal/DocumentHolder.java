package ru.emiren.protocol.DTO.Temporal;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DocumentHolder {
    private byte[] document;
    private LocalDateTime creationTime;

    DocumentHolder(byte[] document) {
        this.document = document;
        this.creationTime = LocalDateTime.now();
    }
}
