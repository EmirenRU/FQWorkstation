package ru.emiren.infosystemdepartment.Service.Word.Impl;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import com.deepoove.poi.XWPFTemplate;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {


    private ResourceLoader resourceLoader;
    private Resource resource;
    InputStream inputStream;

    @Autowired
    public WordServiceImpl(ResourceLoader resourceLoader) throws FileNotFoundException {
        this.resourceLoader = resourceLoader;
        resource = resourceLoader.getResource("classpath:template.docx");
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NiceXWPFDocument generateWordDocument(List<List<String>> data) throws Exception{
        return createWordDocumentByTemplatesPath(data);
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

            NiceXWPFDocument document = new NiceXWPFDocument(inputStream);

//            NiceXWPFDocument document = new NiceXWPFDocument(new FileInputStream(filePath));
            List<NiceXWPFDocument> documents = new ArrayList<>();


            for (int i = 0; i < data.size(); i++) {
                List<String> arr = data.get(i);

                Map<String, Object> dataMap = getStringObjectMap(arr);

                NiceXWPFDocument tempDoc = XWPFTemplate.compile(resource.getFile(), Configure.createDefault()).render(dataMap).getXWPFDocument();


                if (i < data.size() - 1) {
                    XWPFParagraph paragraph = tempDoc.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.addBreak(BreakType.PAGE);
                }
                documents.add(tempDoc);
            }
            document = document.merge(documents, document.getParagraphArray(0).getRuns().get(0));


            return document;
        } catch (IOException e) {
            System.out.println("Something wrong with Doc");
        }
        return null;
    }

    private static Map<String, Object> getStringObjectMap(List<String> arr) {
        for (int i = 0 ; i < arr.size(); i++)
        {
            System.out.println(i + " " + arr.get(i));
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id1", arr.getFirst());

//                dataMap.put("date", LocalDate.now());
//                dataMap.put("orientationCode", "OrientationCode");
//                dataMap.put("orientationName", "orientationName");
        dataMap.put("id2", arr.get(1));
        dataMap.put("id3", arr.get(2));
        dataMap.put("ididk", "???");
//                dataMap.put("departmentName", sl.getStudent().getDepartment().getName());
        dataMap.put("id4", arr.get(3));
        dataMap.put("id5", arr.get(4));
        dataMap.put("id6", arr.get(5));
        dataMap.put("id11", arr.get(11));
        dataMap.put("id12", arr.get(12));
        dataMap.put("id13", arr.get(13));
        dataMap.put("id14", arr.get(14));
        dataMap.put("id15", arr.get(15));
        dataMap.put("id16", arr.get(16));
        dataMap.put("id17", arr.get(21));
        dataMap.put("id18", arr.get(19));
        dataMap.put("id19", arr.get(23));
        dataMap.put("id23", arr.get(24));
        return dataMap;
    }


}
