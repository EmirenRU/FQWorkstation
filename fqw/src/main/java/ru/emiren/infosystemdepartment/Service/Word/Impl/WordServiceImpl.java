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
            document = new NiceXWPFDocument(file);
            List<XWPFTable> tables = document.getTables();

            log.info("The number of tables in file {} is {}", document.getProperties().getThumbnailFilename(), document.getTables().size());
            if (tables.size() == 3) {
                data = processThreeTables(tables);
            } else if (tables.size() == 2){ // check file_name
                data = processTwoTables(tables);
            }
        } catch (IOException e) {
            log.info("Handle later deserialization");
        }
        log.info("data: {}", data);
        return data;
    }

    /**
     *
     * @param tables
     */
    private List<List<String>> processTwoTables(List<XWPFTable> tables) {
        XWPFTable t = tables.getFirst();
        XWPFTableRow headers = t.getRow(0);

        headers.getTableCells().stream().forEach(cell -> {log.info("Header: {}", cell.getText());});


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

                int k = 0;
                for (XWPFTableCell cell : row.getTableCells()) {
                    String header = headers.getTableCells().get(k).getText();
                    keys.put(header, cell.getText());
                    k++;
                }
                log.info("Keys are {}", keys);

                if (map.containsKey(orientation)) {
                    map.get(orientation).get(program).add(keys);
                }
            }
        }
        log.info("The data map contains: {}", map);
        return processData(map);
    }

    private List<List<String>> processData(Map<String, Map<String, List<Map<String, String>>>> dataMap){
        List<List<String>> data = new ArrayList<>();
        /*
            0	ID - autogen
            1	FullName + `Ф.И.О. выпускника`
            2	StudNum + `№ студ. билета`
            3	Theme + `Тема ВКР`
            4	SuData + `Ученая степень, должность руководителя ВКР`
            5	SuName + `Руководитель ВКР`
            11	Questioner1
            12	Question1
            13	Questioner2
            14	Question2
            15	Questioner3
            16	Question3
            21	Score
            20	IndividualOpinion
            24	Language
            29  Department
            30 Orientation
            31 Citizenship + `Гражданство`
            32 Program

            max = 32 -> 32 + 8
            need 1,2,3,4,5,31
         */
        int size = 1;
        log.info("Started processing data");
        for (Map.Entry<String, Map<String, List<Map<String, String>>>> orientations : dataMap.entrySet()) {
            List<String> dat = new ArrayList<>(Collections.nCopies(40, "?"));
            insertAtIndex(dat,0, String.valueOf(size));

            insertAtIndex(dat,30 ,orientations.getKey());
            for (Map.Entry<String, List<Map<String, String>>> programs : orientations.getValue().entrySet()) {
                insertAtIndex(dat,32, programs.getKey());
                for (Map<String, String> keys : programs.getValue()) {
//                    log.info("keys {}, {}, {}, {}, {}, {}"
//                            , keys.get("Ф.И.О. выпускника")
//                            , keys.get("№ студ. билета")
//                            , keys.get("Тема ВКР")
//                            , keys.get("Ученая степень, должность руководителя ВКР")
//                            , keys.get("Руководитель ВКР")
//                            , keys.get("Гражданство"));
                    if (keys.get("Ф.И.О. выпускника").isEmpty()){ continue; }
                    insertAtIndex(dat,31, keys.get("Гражданство"));
                    insertAtIndex(dat,1, keys.get("Ф.И.О. выпускника"));
                    insertAtIndex(dat,2, keys.get("№ студ. билета"));
                    insertAtIndex(dat,3, keys.get("Тема ВКР"));
                    insertAtIndex(dat,4, keys.get("Ученая степень, должность руководителя ВКР"));
                    insertAtIndex(dat,5, keys.get("Руководитель ВКР"));
                    size++;
                    data.add(dat);
                }


            }
        }
        log.info("Endede processing data");
        return data;
    }

    private void insertAtIndex(List<String> list, int index, String value){
        while (list.size() <= index) {
            list.add("?");
        }
        list.set(index, value);
    }

    private List<List<String>> processThreeTables(List<XWPFTable> tables) {
        List<List<String>> data= new ArrayList<>();
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
        return data;
    }

    @Override
    public NiceXWPFDocument generateWordDocument(List<List<String>> data) {
        NiceXWPFDocument document = null;
        this.classPathResource = new ClassPathResource("template_copy.docx");
        log.info("data size: {}", data.getFirst().size());
        File temp_file = null;
        try {
            log.info("Trying to get input stream from template.docx");
            inputStream = classPathResource.getInputStream();
            temp_file = File.createTempFile("template", ".docx");

            try (FileOutputStream outputStream = new FileOutputStream(temp_file)) {
                FileCopyUtils.copy(inputStream, outputStream);
            }
            log.info(temp_file.toString());
            log.info("Ended trying to get input stream from template.docx");

            log.info("Trying to get input stream to NiceXWPFDocument");
            List<NiceXWPFDocument> documents = new ArrayList<>();
            log.info("Ended trying to get input stream to NiceXWPFDocument");


            for (int i = 0; i < data.size()-1; i++) {
                List<String> arr = data.get(i);
                log.info("Array's elements: {}", arr.toString());

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
                log.info("Have deleted the temp_file? {}", temp_file.delete());
            }
        }
        return null;
    }

    private Map<String, Object> getStringObjectMap(List<String> arr) {
        Map<String, Object> dataMap = new HashMap<>();

        Long studNumber = Long.valueOf(arr.get(2));
        dataMap.put("ID", arr.get(0).isEmpty() ? "?" : arr.get(0));
        dataMap.put("FullName", checkArrayBeforeInserting(arr, 1));
        dataMap.put("StudNum", studNumber);
        dataMap.put("Theme", checkArrayBeforeInserting(arr, 3));
        dataMap.put("SuData", checkArrayBeforeInserting(arr, 4)); // Scientific Supervisor
        dataMap.put("SuName", checkArrayBeforeInserting(arr, 5));
        dataMap.put("Questioner1", checkArrayBeforeInserting(arr, 11));
        dataMap.put("Question1", checkArrayBeforeInserting(arr, 12));
        dataMap.put("Questioner2", checkArrayBeforeInserting(arr, 13));
        dataMap.put("Question2", checkArrayBeforeInserting(arr, 14));
        dataMap.put("Questioner3", checkArrayBeforeInserting(arr, 15));
        dataMap.put("Question3", checkArrayBeforeInserting(arr, 16));
        dataMap.put("Score", checkArrayBeforeInserting(arr, 21));
        dataMap.put("IndividualOpinion", checkArrayBeforeInserting(arr, 20));
        dataMap.put("Language", checkArrayBeforeInserting(arr, 24));

        dataMap.putIfAbsent("Department", "?");
        dataMap.putIfAbsent("Orientation", "?");

    /*
        String department = sqlService.getDepartmentNameByStudentNumber(studNumber);
        String orientationCodeWithName = sqlService.getOrientationCodeWithNameByStudentNumber(studNumber);

        if (department != null) {
            dataMap.put("Department", department);
        }
        if (orientationCodeWithName != null) {
            dataMap.put("Orientation", orientationCodeWithName);
        }
    */


    /*
        dataMap.put("Answer1", checkArrayBeforeInserting(arr, <index_for_answer1>));
        dataMap.put("Answer2", checkArrayBeforeInserting(arr, <index_for_answer2>));
        dataMap.put("Answer3", checkArrayBeforeInserting(arr, <index_for_answer3>));
        dataMap.put("Estimation", checkArrayBeforeInserting(arr, <index_for_estimation>));
    */

        return dataMap;
    }

    private String checkArrayBeforeInserting(List<String> arr, int index) {

        if (index < arr.size()) {
            return arr.get(index).isEmpty() ? "?" : arr.get(index);
        } else {
            log.warn("Index {} is out of bounds for array: {}", index, arr);
            return "?";
        }
    }





}
