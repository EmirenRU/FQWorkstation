package ru.emiren.infosystemdepartment.Model.Temporal;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
