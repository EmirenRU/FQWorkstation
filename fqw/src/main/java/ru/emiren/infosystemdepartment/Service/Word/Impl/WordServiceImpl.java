package ru.emiren.infosystemdepartment.Service.Word.Impl;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import com.deepoove.poi.XWPFTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;
import ru.emiren.infosystemdepartment.Model.SQL.Student;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;
import ru.emiren.infosystemdepartment.Service.SQL.SqlService;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WordServiceImpl implements WordService {

    private final SqlService sqlService;
    private InputStream inputStream;
    private ClassPathResource classPathResource;
    private Pattern pattern = Pattern.compile("[\\d]{2}[.][\\d]{2}[.][\\d]{2}");

    @Autowired
    public WordServiceImpl(ResourceLoader resourceLoader, SqlService sqlService) {
        this.sqlService = sqlService;
    }

    @Override
    public List<String> processTable(XWPFTable table, int indexRow, int numCells) {
        XWPFTableRow row = table.getRow(indexRow);
        return row.getTableCells().stream()
                .map(XWPFTableCell::getText)
                .toList();
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

            log.info("The number of tables in file {} is {}", document.getProperties().getThumbnailFilename(), document.getTables().size());
            if (tables.size() == 3) {
                processThreeTables(tables, data);
            } else if (tables.size() == 2){ // check file_name
                processTwoTables(tables, data);
            }
        } catch (IOException e) {
            log.info("Handle later deserialization");
        }
        return data;
    }


    /**
     *
     * @param tables
     * @param data
     */
    private void processTwoTables(List<XWPFTable> tables, List<List<String>> data) {
        XWPFTable t = tables.getFirst();
        XWPFTableRow headers = t.getRow(0);


        Map<String, Map<String, List<Map<String, String>>>> map = new HashMap<>();
        String orientation = "";
        String program = "";
        for (int i = 2; i < t.getNumberOfRows(); i++) {

            XWPFTableRow row = t.getRow(i);
            if (row.getTableCells().size() == 1 ) {
                String text = row.getTableCells().get(0).getText();

                log.info("Was called with getTableCells.size == 1: {}", text);
                if (text.contains("«")){
                    program = text.substring(1, text.length() - 1);
                    log.info("The program is {}", program);

                    if (!orientation.isEmpty()) {
                        map.putIfAbsent(orientation, new HashMap<>());
                        map.get(orientation).putIfAbsent(program, new ArrayList<>());
                    }
                } if (pattern.matcher(text).find()) {
                    orientation = text;
                    log.info("The orientation is {}", orientation);
                    map.putIfAbsent(orientation, new HashMap<>());
                }
                if (!orientation.isEmpty() && !program.isEmpty()) {
                    log.info("The orientation and program are {}, {}", orientation, program);
                }
//                log.info("Map is {}", map);
            } else {

                Map<String, String> keys = new HashMap<>();
                headers.getTableCells().forEach(cells -> keys.putIfAbsent(cells.getText(), ""));
                log.info("Keys are {}", keys);
                int k = 0;
                for (String key : keys.keySet()) {
                    keys.put(key,row.getTableCells().get(k).getText());
                    k++;
                }

                if (map.containsKey(orientation)) {
                    map.get(orientation).get(program).add(keys);
                }
            }
        }
        log.info("The data map contains: {}", map);
//        processData(map);
    }

    private void processData(Map<String, Map<String, Map<String, String>>> dataMap){

        for (Map.Entry<String, Map<String, Map<String, String>>> orientations : dataMap.entrySet()) {
            for (Map.Entry<String, Map<String, String>> programs : orientations.getValue().entrySet()) {
                for (Map.Entry<String, String> entry : programs.getValue().entrySet()) {

                }
            }
        }
    }

    private void processThreeTables(List<XWPFTable> tables, List<List<String>> data) {
        XWPFTable t1 = tables.getFirst();
        XWPFTable t2 = tables.get(1);
        XWPFTable t3 = tables.getLast();

        if (t1.getNumberOfRows() == t2.getNumberOfRows() && t2.getNumberOfRows() == t3.getNumberOfRows()) {
            for (int i = 1; i < t1.getNumberOfRows(); i++) {
                XWPFTableRow r1 = t1.getRow(i);
                XWPFTableRow r2 = t2.getRow(i);
                XWPFTableRow r3 = t3.getRow(i);

                log.info("The part of table 1: {}", t1.getPart());
                log.info("The part of table 2: {}", t2.getPart());
                log.info("The part of table 3: {}", t3.getPart());

                List<String> innerArray = new ArrayList<>() {};

                innerArray.addAll(processTable(t1, i, r1.getTableCells().size()));
                innerArray.addAll(processTable(t2, i, r2.getTableCells().size()));
                innerArray.addAll(processTable(t3, i, r3.getTableCells().size()));

                data.add(innerArray);
            }
        }
    }

    @Override
    public NiceXWPFDocument generateWordDocument(List<List<String>> data) {
        NiceXWPFDocument document = null;
        this.classPathResource = new ClassPathResource("template.docx");
        File temp_file = null;
        try {
            log.info("Trying to get input stream from template.docx");
            inputStream = classPathResource.getInputStream();
            temp_file = File.createTempFile("template", ".docx");

            try (FileOutputStream outputStream = new FileOutputStream(temp_file)) {
                FileCopyUtils.copy(inputStream, outputStream);
            }
            log.info("Ended trying to get input stream from template.docx");

            log.info("Trying to get input stream to NiceXWPFDocument");
            List<NiceXWPFDocument> documents = new ArrayList<>();
            log.info("Ended trying to get input stream to NiceXWPFDocument");


            for (int i = 0; i < data.size()-1; i++) {
                List<String> arr = data.get(i);

                Map<String, Object> dataMap = getStringObjectMap(arr);

                log.info("The dataMap contains: {}", dataMap);

                NiceXWPFDocument tempDoc = XWPFTemplate.compile(temp_file, Configure.createDefault()).render(dataMap).getXWPFDocument();


                if (i < data.size()) {
                    XWPFParagraph paragraph = tempDoc.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    if (i != data.size() - 2) {
                        run.addBreak(BreakType.PAGE);
                    }
                }
                documents.add(tempDoc);
            }

            document = documents.getLast();
            documents.removeLast();
            document = document.merge(documents, document.getParagraphArray(0).getRuns().getFirst());

            log.info("closing the documents list");
            for (NiceXWPFDocument doc : documents) { doc.close(); } // Stream.map does not provide without try_catch
            log.info("Done closing the documents list");

            return document;
        } catch (Exception e) {
            log.warn("WordService: {}", e.getMessage());
        } finally {
            if (temp_file != null) {
                log.info("Have deleted the temp_file? {}",temp_file.delete());
            }
        }
        return null;
    }

    private Map<String, Object> getStringObjectMap(List<String> arr) {
        Map<String, Object> dataMap = new HashMap<>();
        Long studNumber = Long.valueOf(arr.get(2));
        dataMap.put("ID", arr.getFirst());

        dataMap.put("FullName", arr.get(1));
        dataMap.put("StudNum", studNumber);

        dataMap.put("Theme", arr.get(3));

        dataMap.put("SuData", arr.get(4)); // Scientific Supervisor
        dataMap.put("SuName", arr.get(5));

        dataMap.put("Questioner1", arr.get(11));
        dataMap.put("Question1", arr.get(12));
        dataMap.put("Questioner2", arr.get(13));
        dataMap.put("Question2", arr.get(14));
        dataMap.put("Questioner3", arr.get(15));
        dataMap.put("Question3", arr.get(16));

        dataMap.put("Score", arr.get(21));

        dataMap.put("IndividualOpinion", arr.get(20));

        dataMap.put("Language", arr.get(24));

        String department = sqlService.getDepartmentNameByStudentNumber(studNumber);
        String orientationCodeWithName = sqlService.getOrientationCodeWithNameByStudentNumber(studNumber);

        // Program, AnswerI I in {1-3}, Estimation
        dataMap.put("Department",  department);
        dataMap.put("Orientation", orientationCodeWithName);
//        dataMap.put("Program", ); // пока самая непонятная
//        dataMap.put("Answer1", );
//        dataMap.put("Answer2", );
//        dataMap.put("Answer3", );
//        dataMap.put("Estimation", );

        sqlService.saveDataFromProtocol(dataMap);
        return dataMap;
    }





}
