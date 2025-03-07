package ru.emiren.protocol.DTO.Temporal;

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

@Getter
public class DocumentHolder {
    private final byte[] document;
    private final byte[] template;
    private final LocalDateTime creationTime;


    DocumentHolder(byte[] document, byte[] template) {
        this.document = document;
        this.creationTime = LocalDateTime.now();
        this.template = template;
    }

    DocumentHolder(byte[] document) {
        this.document = document;
        this.creationTime = LocalDateTime.now();
        this.template = null;
    }
}
