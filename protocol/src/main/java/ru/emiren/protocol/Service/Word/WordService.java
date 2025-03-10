package ru.emiren.protocol.Service.Word;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface WordService {
    NiceXWPFDocument generateWordDocument(List<List<String>> data);
    NiceXWPFDocument generateWordDocument(List<List<String>> data, File fileTemplate);
    List<String> processTable(XWPFTable table, int indexRow , int numCells);
    List<List<String>> getListOfDataFromFile(InputStream file, String fileName);
    CompletableFuture<Void> saveDataAsync(Map<String, Object> dataMap);
}
