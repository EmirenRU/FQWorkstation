package ru.emiren.protocol.Service.Word;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ContestService {
    NiceXWPFDocument generateWordDocument(InputStream is, Map<String, Object> data);
    List<String> processTable(XWPFTable table, int indexRow , int numCells);
    void saveDataAsync(Map<String, Object> dataMap);
}
