package ru.emiren.infosystemdepartment.Service.Word.Impl;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.util.ResourceUtils;
import com.deepoove.poi.XWPFTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {

    private File filePath;

    @Override
    public NiceXWPFDocument generateWordDocument(List<List<String>> data) throws Exception{
        NiceXWPFDocument doc = createWordDocumentByTemplatesPath(data);
        return doc;
    }

    @Override
    public List<Map<String, String>> handleUploadFile(NiceXWPFDocument document) throws Exception {
        return List.of();
    }

    @Override
    public List<String> processTable(XWPFTable table, int indexRow, int numCells) {
        XWPFTableRow row = table.getRow(indexRow);
        return row.getTableCells().stream()
                .map(XWPFTableCell::getText)
                .collect(Collectors.toList());
    }

    private XWPFDocument createWordDocument() {
        return new XWPFDocument();
    }

    private NiceXWPFDocument createWordDocumentByTemplatesPath(List<List<String>> data)
            throws Exception
    {
        try {
            filePath = ResourceUtils.getFile("classpath:temp.docx");

            NiceXWPFDocument document = new NiceXWPFDocument();

            for (int i = 0; i < data.size(); i++) {
                List<String> arr = data.get(i);

                Map<String, Object> dataMap = getStringObjectMap(arr);

                NiceXWPFDocument tempDoc = XWPFTemplate.compile(filePath).render(dataMap).getXWPFDocument();

                if (i < data.size() - 1) {
                    XWPFParagraph paragraph = tempDoc.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.addBreak(org.apache.poi.xwpf.usermodel.BreakType.PAGE);
                }
                document = document.merge(tempDoc);
            }

            return document;
        } catch (IOException e) {
            System.out.println("Something wrong with Doc");
        }
        return null;
    }

    private static Map<String, Object> getStringObjectMap(List<String> arr) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id1", arr.getFirst());
//                dataMap.put("date", LocalDate.now());
//                dataMap.put("orientationCode", "OrientationCode");
//                dataMap.put("orientationName", "orientationName");
        dataMap.put("id02", arr.get(1));
        dataMap.put("id03", arr.get(2));
//                dataMap.put("departmentName", sl.getStudent().getDepartment().getName());
        dataMap.put("id04", arr.get(3));
        dataMap.put("id05", arr.get(4));
        dataMap.put("id06", arr.get(5));
        dataMap.put("id07", arr.get(6));
        dataMap.put("id08", arr.get(7));
        dataMap.put("id09", arr.get(8));
        dataMap.put("id10", arr.get(9));
        dataMap.put("id11", arr.get(11));
        dataMap.put("id12", arr.get(12));
        dataMap.put("id13", arr.get(13));
        dataMap.put("id14", arr.get(14));
        dataMap.put("id15", arr.get(15));
        dataMap.put("id16", arr.get(16));
        dataMap.put("id17", arr.get(18));
        dataMap.put("id18", arr.get(19));
        dataMap.put("id19", arr.get(20));
        dataMap.put("id20", arr.get(21));
        dataMap.put("id21", arr.get(22));
        dataMap.put("id22", arr.get(23));
        dataMap.put("id23", arr.get(24));
        return dataMap;
    }


}
