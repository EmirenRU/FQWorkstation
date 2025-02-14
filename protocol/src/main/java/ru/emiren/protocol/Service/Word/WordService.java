package ru.emiren.protocol.Service.Word;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface WordService {
    public NiceXWPFDocument generateWordDocument(List<List<String>> data);
    public List<String> processTable(XWPFTable table, int indexRow , int numCells);
    public List<List<String>> getListOfDataFromFile(InputStream file);
    public CompletableFuture<Void> saveDataAsync(Map<String, Object> dataMap);
}
