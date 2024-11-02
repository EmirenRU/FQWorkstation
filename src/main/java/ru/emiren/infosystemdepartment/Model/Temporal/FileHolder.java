package ru.emiren.infosystemdepartment.Model.Temporal;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@Component
public class FileHolder {
    private ConcurrentHashMap<String, DocumentHolder> holder = new ConcurrentHashMap<>();

    public void storeDocument(String id, byte[] document) {
        holder.put(id, new DocumentHolder(document));
    }

    public byte[] getDocument(String id) {
        DocumentHolder doc = holder.get(id);
        return doc != null ? doc.getDocument() : null;
    }

    public DocumentHolder removeDocument(String id) {
        return holder.remove(id);
    }

    public boolean containsDocument(String id) {
        return holder.containsKey(id);
    }

}
