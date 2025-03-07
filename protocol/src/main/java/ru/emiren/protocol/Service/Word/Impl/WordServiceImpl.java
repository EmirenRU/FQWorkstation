package ru.emiren.protocol.Service.Word.Impl;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import com.deepoove.poi.XWPFTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.emiren.protocol.Service.Word.WordService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Service
@Slf4j
public class WordServiceImpl implements WordService {

    @Value("${server.fqw.url}")
    private String sqlLocation;

    private final RestTemplate restTemplate;
    private InputStream inputStream;
    private ClassPathResource classPathResource;
    private Pattern pattern = Pattern.compile("[\\d]{2}[.][\\d]{2}[.][\\d]{2}");

    @Autowired
    public WordServiceImpl(ResourceLoader resourceLoader, RestTemplate restTemplate) {
        log.info("Loading Word Service");
        log.info("FQW Location: {}", sqlLocation);
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> processTable(XWPFTable table, int indexRow, int numCells) {
        XWPFTableRow row = table.getRow(indexRow);
        return row.getTableCells().stream()
                .map(XWPFTableCell::getText)
                .toList();
    }

    @Override
    public List<List<String>> getListOfDataFromFile(InputStream file, String fileName) {
        List<List<String>> data = List.of();
        String ext = getFileExtension(fileName);
        if (ext != null) {
            if (ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls")) {
                data = processExcelFile(file);
            } else if (ext.equalsIgnoreCase("docx") || ext.equalsIgnoreCase("doc")) {
                data = processWordFile(file);
            } else {
                log.error("Unsupported file format");
            }
        }
        return data;
    }

    private String getFileExtension(String fileName){
        if (!fileName.isEmpty()){
            return StringUtils.getFilenameExtension(fileName);
        }
        return null;
    }

    private List<List<String>> processWordFile(InputStream file){
        List<List<String>> data = List.of();
        try (XSSFWorkbook workbook = new XSSFWorkbook(file)){
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                data = processSheet(sheet);
            }
        } catch (IOException ex){
            log.error(ex.getMessage());
        }
        log.info("data: {}", data);
        return data;
    }

    private List<List<String>> processExcelFile(InputStream file){
        List<List<String>> data = List.of();
        try (NiceXWPFDocument document = new NiceXWPFDocument(file)){
            List<XWPFTable> tables = document.getTables();

            log.info("The number of tables in file {} is {}",
                    document.getProperties().getThumbnailFilename(),
                    document.getTables().size());
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

    private List<List<String>> processSheet(XSSFSheet sheet){
        Row header = sheet.getRow(0);
        header.forEach(cell -> {log.info("Header: {}", cell.toString());});

        // Starting implementation of Excel Handler
        // TODO to finish
        List<List<String>> data = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(cell.getStringCellValue());
            }
            log.info("Row with index {} : {}", i , rowData);
            data.add(rowData);
        }

        return null;
    }

    /**
     *
     * @param tables
     */
    private List<List<String>> processTwoTables(List<XWPFTable> tables) {
        XWPFTable t = tables.getFirst();
        XWPFTableRow headers = t.getRow(0);

        headers.getTableCells().forEach(cell -> {log.info("Header: {}", cell.getText());});

        // I don't remember what he is doing :(. Only remember that it contains all rows, but why is there so much maps, I simply do not know
        // Outer map is for Orientation Code; Inner Map is for Program, And list is for structured data
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
//                    log.info("keys {}, {}, {}, {}, {}, {}", keys.get("Ф.И.О. выпускника"), keys.get("№ студ. билета"), keys.get("Тема ВКР"), keys.get("Ученая степень, должность руководителя ВКР"), keys.get("Руководитель ВКР"), keys.get("Гражданство"));
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
    public NiceXWPFDocument generateWordDocument(List<List<String>> data, File fileTemplate) {
        log.info("data size: {}", data.get(0).size());
        return generateDocument(data, fileTemplate);
    }

    @Override
    public NiceXWPFDocument generateWordDocument(List<List<String>> data) {
        log.info("data size: {}", data.get(0).size());
        File tempFile = createTempFileFromClassPathResource("template_copy.docx");
        if (tempFile == null) {
            return null;
        }
        return generateDocument(data, tempFile);
    }

    private File createTempFileFromClassPathResource(String resourcePath) {
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);
        File tempFile = null;
        try (InputStream inputStream = classPathResource.getInputStream()) {
            tempFile = File.createTempFile("template", ".docx");
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                FileCopyUtils.copy(inputStream, outputStream);
            }
            log.info("Temporary file created: {}", tempFile);
        } catch (IOException e) {
            log.warn("Failed to create temporary file from resource: {}", e.getMessage());
        }
        return tempFile;
    }

    private NiceXWPFDocument generateDocument(List<List<String>> data, File fileTemplate) {
        NiceXWPFDocument document = null;

        try {
            List<NiceXWPFDocument> documents = new ArrayList<>();

            for (int i = 0; i < data.size() - 1; i++) {
                List<String> arr = data.get(i);
                Map<String, Object> dataMap = getStringObjectMap(arr);

                log.info("The dataMap contains: {}", dataMap);
                try {
                    saveDataAsync(new HashMap<>(dataMap));
                } catch (RestClientException e){
                    log.warn("RestClientException: {}", e.getMessage());
                } catch (Exception e) {
                    log.warn("Async Exception: {}", e.getMessage());
                }
                NiceXWPFDocument tempDoc = XWPFTemplate.compile(fileTemplate, Configure.createDefault())
                        .render(dataMap)
                        .getXWPFDocument();

                addPageBreak(tempDoc, i, data.size());
                documents.add(tempDoc);
            }

            document = documents.get(documents.size() - 1);
            documents.remove(documents.size() - 1);
            document = document.merge(documents, document.getParagraphArray(0).getRuns().get(0));

            log.info("Closing the documents list");
            for (NiceXWPFDocument doc : documents) {
                doc.close();
            }
            log.info("Done closing the documents list");

            boolean flag = Files.deleteIfExists(Path.of(fileTemplate.getPath()));
            log.info("Have deleted the temp_file? {}", flag);
            return document;
        } catch (Exception e) {
            log.warn("WordService: {}", e.getMessage());
        }
        return null;
    }

    private void addPageBreak(NiceXWPFDocument tempDoc, int currentIndex, int totalSize) {
        XWPFParagraph paragraph = tempDoc.createParagraph();
        XWPFRun run = paragraph.createRun();
        if (currentIndex != totalSize - 2) {
            run.addBreak(BreakType.PAGE);
        }
    }



    private Map<String, Object> getStringObjectMap(List<String> arr) {
        log.info("started processing data for id {}", arr.get(0));
        Map<String, Object> dataMap = new HashMap<>();

        Long studNumber = Long.valueOf(arr.get(2));
        dataMap.put("ID", arr.get(0).isEmpty() ? "?" : arr.get(0));
        dataMap.put("FullName", checkArrayBeforeInserting(arr, 1));
        dataMap.put("StudNum", studNumber);
        dataMap.put("Theme", checkArrayBeforeInserting(arr, 3));
        dataMap.put("SuData", checkArrayBeforeInserting(arr, 4));
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
        log.info("Before transfering REST GET method");
        Map<String, String> map = new HashMap<>();
        String departmentName = "?";
        String orientationCodeWithName = "?";
        try {
            log.info("After transfering REST GET method");
            map = (Map<String, String>) restTemplate.getForObject(sqlLocation + "/api/v1/get-department-and-orientation/" + studNumber,
                    Map.class);
            departmentName = map.get("Department");
            orientationCodeWithName = map.get("Orientation");
            log.info("After transfering REST GET method");
        } catch (RestClientException e){
            log.warn("RestClientException: {}", e.getMessage());
        }
        log.info("DeparmentName and orientationCodeWithName: {}; {}", departmentName, orientationCodeWithName);
        if (departmentName != null && !departmentName.equals("?")) {
            dataMap.put("Department", departmentName);
        } else {
            dataMap.put("Department", "?");
        }
        if (orientationCodeWithName != null && !orientationCodeWithName.equals("?")) {
            dataMap.put("Orientation", orientationCodeWithName);
        } else {
            dataMap.put("Orientation", "?");
        }

        dataMap.put("Answer1", "?");
        dataMap.put("Answer2", "?");
        dataMap.put("Answer3", "?");
        String score = checkArrayBeforeInserting(arr, 21);
        log.info("score: {}", score );
        if (score != null && !score.contains("?")) {
            long scoreNumber = Long.parseLong(score.substring(0, 3).trim()); // First 3 numbers, if it will not happen, fix it
            log.info("scoreNumber: {}", scoreNumber);
            if (scoreNumber < 51L){
                dataMap.put("Estimation", "Плохо");
            } else if (scoreNumber < 69L){
                dataMap.put("Estimation", "Удовлетворительно");
            } else {
                dataMap.put("Estimation", "Отлично");
            }
        } else {
            dataMap.put("Estimation", "?");
        }
        log.info("Ended processing score: {}", score);
        dataMap.put("IndividualOpinion", "?");

        return dataMap;
    }

    @Async("asyncTaskExecutor")
    @Override
    public CompletableFuture<Void> saveDataAsync(Map<String, Object> dataMap) {
        String studentNumber = String.valueOf(dataMap.get("StudNum"));

        log.info("Tring to ", studentNumber);
        ResponseEntity<?> responseEntity = restTemplate.postForEntity(sqlLocation + "/api/v1/save-data", dataMap, ResponseEntity.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Saving the dataMap with student number {} is {}", studentNumber, responseEntity.getBody());
        }
        return CompletableFuture.completedFuture(null);
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
