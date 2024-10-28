package ru.emiren.infosystemdepartment.Service.Word.Impl;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import com.deepoove.poi.XWPFTemplate;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {

//    @Value("${template.file.path}")
//    private final String TEMPLATE_PATH;
    @Value("classpath:template.docx")
    private Resource resource;
    private InputStream inputStream;
    private ResourceLoader resourceLoader;

    @Autowired
    public WordServiceImpl(@Value("${template.file.path}") String TEMPLATE_PATH, ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        resource = resourceLoader.getResource(TEMPLATE_PATH);
        //        this.TEMPLATE_PATH = "classpath:template.docx";
//        resource = resourceLoader.getResource(this.TEMPLATE_PATH);
        try {
            inputStream = resource.getInputStream();

        } catch (IOException e) {
            System.out.println();
            System.out.println("\nSomething wrong with Template Reading");
            System.out.println(e.getMessage());
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

    @Override
    public List<List<String>> getListOfDataFromFile(InputStream file) {
        List<List<String>> data = List.of();
        NiceXWPFDocument document;

        if (data == null) {
            data = new ArrayList<>();
        }

        if (!data.isEmpty()) {
            for (List<String> row : data)
                row.clear();
            data.clear();
        }

        try {
            data = new ArrayList<>();
            document = new NiceXWPFDocument(file);
            List<XWPFTable> tables = document.getTables();

            if (tables.size() == 3) {
                XWPFTable t1 = tables.get(0);
                XWPFTable t2 = tables.get(1);
                XWPFTable t3 = tables.get(2);

                log.info(t1.getNumberOfRows() + " " + t2.getNumberOfRows() + " " + t3.getNumberOfRows());

                if (t1.getNumberOfRows() == t2.getNumberOfRows() && t2.getNumberOfRows() == t3.getNumberOfRows()) {
                    for (int i = 1; i < t1.getNumberOfRows(); i++) {
                        XWPFTableRow r1 = t1.getRow(i);
                        XWPFTableRow r2 = t2.getRow(i);
                        XWPFTableRow r3 = t3.getRow(i);


                        List<String> innerArray = new ArrayList<>() {
                        };

                        innerArray.addAll(wordService.processTable(t1, i, r1.getTableCells().size()));
                        innerArray.addAll(wordService.processTable(t2, i, r2.getTableCells().size()));
                        innerArray.addAll(wordService.processTable(t3, i, r3.getTableCells().size()));

                        data.add(innerArray);
                    }
                }
            }
        } catch (IOException e) {
            log.info("Handle later deserialization");
        }
        return data;
    }

    private NiceXWPFDocument createWordDocumentByTemplatesPath(List<List<String>> data)
            throws Exception
    {
        NiceXWPFDocument document = null;
        try {


            document = new NiceXWPFDocument(inputStream);
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
            e.printStackTrace();
        }
        return document;
    }

    private static Map<String, Object> getStringObjectMap(List<String> arr) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id1", arr.getFirst());
        dataMap.put("id2", arr.get(1));
        dataMap.put("id3", arr.get(2));
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
        dataMap.put("id19", arr.get(20));
        dataMap.put("id23", arr.get(24));
        return dataMap;
    }


}
