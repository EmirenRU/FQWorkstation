package ru.emiren.protocol.Service.Deserialization.Impl;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.protocol.Service.Deserialization.DeserializationService;
import ru.emiren.protocol.Service.Word.WordService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DeserializationServiceImpl implements DeserializationService {
    @Autowired
    private WordService wordService;

    @Override
    public List<List<String>> deserializeFile(InputStream inputStream) {
        List<List<String>> data = new ArrayList<>();

        try (NiceXWPFDocument document = new NiceXWPFDocument(inputStream)) {
            List<XWPFTable> tables = document.getTables();

            if (tables.size() == 3) {
                XWPFTable t1 = tables.get(0);
                XWPFTable t2 = tables.get(1);
                XWPFTable t3 = tables.get(2);

                if (t1.getNumberOfRows() == t2.getNumberOfRows() && t2.getNumberOfRows() == t3.getNumberOfRows()) {
                    for (int i = 1; i < t1.getNumberOfRows(); i++) {
                        List<String> innerArray = new ArrayList<>();
                        innerArray.addAll(wordService.processTable(t1, i, t1.getRow(i).getTableCells().size()));
                        innerArray.addAll(wordService.processTable(t2, i, t2.getRow(i).getTableCells().size()));
                        innerArray.addAll(wordService.processTable(t3, i, t3.getRow(i).getTableCells().size()));
                        data.add(innerArray);
                    }
                }
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return data;
    }
}
