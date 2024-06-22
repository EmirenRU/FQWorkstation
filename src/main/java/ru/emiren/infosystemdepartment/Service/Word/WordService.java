package ru.emiren.infosystemdepartment.Service.Word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.List;
import java.util.Map;

public interface WordService {

    public NiceXWPFDocument generateWordDocument(List<List<String>> data) throws Exception;
    public List<Map<String, String>> handleUploadFile(NiceXWPFDocument document) throws Exception;
    List<String> processTable(XWPFTable table, int indexRow , int numCells);

}
