package ru.emiren.infosystemdepartment.Service.Word;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.InputStream;
import java.util.List;

public interface WordService {

    public NiceXWPFDocument generateWordDocument(List<List<String>> data);
    public List<String> processTable(XWPFTable table, int indexRow , int numCells);
    public List<List<String>> getListOfDataFromFile(InputStream file);
}
