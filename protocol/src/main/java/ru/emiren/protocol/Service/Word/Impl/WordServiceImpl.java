package ru.emiren.protocol.Service.Word.Impl;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import com.deepoove.poi.XWPFTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.emiren.protocol.Service.Word.WordService;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

@Service
@Slf4j
public class WordServiceImpl implements WordService {

    private final Gson gson;
    private String sqlLocation;

    private final RestTemplate restTemplate;
    private Pattern pattern = Pattern.compile("[\\d]{2}[.][\\d]{2}[.][\\d]{2}");

    @Autowired
    public WordServiceImpl(ResourceLoader resourceLoader, String sqlLocation, RestTemplate restTemplate, Gson gson) {
        this.sqlLocation = sqlLocation;
        log.info("Loading Word Service");
        log.info("FQW Location: {}", sqlLocation);
        this.restTemplate = restTemplate;
        this.gson = gson;
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
        log.info("Extension: {}", ext);
        if (ext != null) {
            if (ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls")) {
                log.info("Loading XLSX Data");
                data = processExcelFile(file);
            } else if (ext.equalsIgnoreCase("docx") || ext.equalsIgnoreCase("doc")) {
                log.info("Loading DOCX Data");
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
        try (NiceXWPFDocument document = new NiceXWPFDocument(file)){
            List<XWPFTable> tables = document.getTables();

            log.info("The number of tables in file {} is {}",
                    document.getProperties().getThumbnailFilename(),
                    document.getTables().size());
            if (tables.size() == 3) {
                data = processThreeTables(tables);
            } else if (tables.size() == 2){ // check file_name
                XWPFWordExtractor extractor = new XWPFWordExtractor(document);
                String documentText = extractor.getText();
                String extractedText = "?";
                int index = documentText.indexOf("Факультет ");
                if (index != -1) {
                    extractedText = documentText.substring(index + 10).trim().split("\n")[0];
                    extractedText = extractedText.substring(0, 1).toUpperCase() + extractedText.substring(1);
                    log.info("Extracted text: " + extractedText);
                } else {
                    log.info("Keyword not found.");
                }
                data = processTwoTables(tables);
                for (List<String> row : data) {
                    row.set(29, extractedText);
                }
            }
        } catch (IOException e) {
            log.info("Handle later deserialization");
        }
        log.info("data: {}", data);
        return data;
    }

    private List<List<String>> processExcelFile(InputStream file){
        List<List<String>> data = List.of();
        try (XSSFWorkbook workbook = new XSSFWorkbook(file)){
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                log.info("Processing Sheet {} with name {}", i, sheet.getSheetName());
                data = processSheet(sheet);
            }
        } catch (IOException ex){
            log.error(ex.getMessage());
        }
        log.info("data: {}", data);
        return data;

    }

    private List<List<String>> processSheet(XSSFSheet sheet){
        Row header = sheet.getRow(0);
        header.forEach(cell -> {log.info("Header: {}", cell.toString());});
        header = translateCellsToEngVariation(header);

        // TODO to finish
        List<Map<String, String>> mapList = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            Map<String, String> rowData = new HashMap<>();
            rowData.putIfAbsent("ID", String.valueOf(i));
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                if (cell != null){
                    if (cell.getCellType() == CellType.STRING) {
                        log.info("Data is {}", cell.getStringCellValue());
                        rowData.putIfAbsent(header.getCell(j).getStringCellValue(), cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        rowData.putIfAbsent(header.getCell(j).getStringCellValue(), String.valueOf(cell.getNumericCellValue()));
                    } else if (cell.getCellType() == CellType.BLANK) {
                        rowData.putIfAbsent(header.getCell(j).getStringCellValue(), " ");
                    }
                } else {
                    rowData.putIfAbsent(header.getCell(j).getStringCellValue(), " ");
                }
            }
            log.info("Row with index {} : {}", i , rowData);
            mapList.add(rowData);
        }



        return processData(mapList);
    }



    private Row translateCellsToEngVariation(Row header) {
        HashMap<String, String> rowData = new HashMap<>(Map.of("ФИО Преподавателя", "SuName",
                "Ученная степень", "AcDegree",
                "Должность", "Position",
                "Кафедра", "Department",
                "ФИО Студента", "FullName",
                "Студ.Номер", "StudNum",
                "Гражданство", "Citizenship",
                    "Тема", "Theme"
        ));

        header.forEach(cell -> {
            if (rowData.containsKey(cell.getStringCellValue())) {
                cell.setCellValue(rowData.get(cell.getStringCellValue()));
            }
        });
        return header;
    }

    private List<List<String>> processData(List<Map<String, String>> mapList) {
        List<List<String>> data = new ArrayList<>();

        for (Map<String, String> map : mapList) {
            List<String> rowData = new ArrayList<>(Collections.nCopies(42, " "));
            log.info("map in process: {}", map);
            rowData.set(0, map.getOrDefault("ID", " "));
            rowData.set(1, map.getOrDefault("FullName", " "));
            rowData.set(2, map.getOrDefault("StudNum", " "));
            rowData.set(3, map.getOrDefault("Theme", " "));
            rowData.set(4, (map.get("SuData") != null) ? map.getOrDefault("SuData", " ") : (map.getOrDefault("AcDegree", " ") + " " + map.getOrDefault("Position", " ")));
            rowData.set(5, map.getOrDefault("SuName", " "));
            rowData.set(11, map.getOrDefault("Questioner1", " "));
            rowData.set(12, map.getOrDefault("Question1", " "));
            rowData.set(13, map.getOrDefault("Questioner2", " "));
            rowData.set(14, map.getOrDefault("Question2", " "));
            rowData.set(15, map.getOrDefault("Questioner3", " "));
            rowData.set(16, map.getOrDefault("Question3", " "));
            rowData.set(20, map.getOrDefault("IndividualOpinion", " "));
            rowData.set(24, map.getOrDefault("Language", " "));
            rowData.set(29, map.getOrDefault("Department", " "));
            rowData.set(30, map.getOrDefault("Orientation", " "));
            rowData.set(21, map.getOrDefault("Score", " "));
            rowData.set(31, map.getOrDefault("Citizenship", " "));
            rowData.set(32, map.getOrDefault("Program", " "));
            rowData.set(33, map.getOrDefault("NumberOfDecree", " "));
            log.info("rowdata : {}", rowData);

            data.add(rowData);
        }
        log.info("data after processing: {}", data);

        return data;
    }


    private String orientation = "";
    private String program = "";
    /**
     *
     * @param tables
     */
    private List<List<String>> processTwoTables(List<XWPFTable> tables) {
        XWPFTable t = tables.getFirst();
        XWPFTableRow headers = t.getRow(0);

        headers.getTableCells().forEach(cell -> {log.info("Header: {}", cell.getText());});
        Map<String, Map<String, List<Map<String, String>>>> map = new HashMap<>();


        for (int i = 2; i < t.getNumberOfRows(); i++) {
            XWPFTableRow row = t.getRow(i);

            if (row.getTableCells().size() == 1) {
                String text = row.getTableCells().get(0).getText();
                log.info("Was called with getTableCells.size == 1: {}", text);

                if (pattern.matcher(text).find()) {
                    orientation = text;
                    log.info("The orientation is {}", orientation);
                    program = "";
                    map.putIfAbsent(orientation, new HashMap<>());
                    continue;
                } else if (text.contains("«")) {
                    program = text.substring(1, text.length() - 1);
                    log.info("The program is {}", program);
                    map.get(orientation).putIfAbsent(program, new ArrayList<>());
                    continue;
                }

                if (!orientation.isEmpty() && !program.isEmpty()) {
                    log.info("The orientation and program are {}, {}", orientation, program);
                }
                log.info("Map is {}", map);
            } else {
                if (orientation.isEmpty() || program.isEmpty()) {
                    log.warn("Skipping data row - orientation or program not set");
                    continue;
                }

                Map<String, String> keys = new HashMap<>();
                headers.getTableCells().forEach(cell -> keys.put(cell.getText(), ""));

                for (int k = 0; k < row.getTableCells().size(); k++) {
                    XWPFTableCell cell = row.getTableCells().get(k);
                    if (k < headers.getTableCells().size()) {
                        String header = headers.getTableCells().get(k).getText();
                        keys.put(header, cell.getText());
                    }
                }
                log.info("Keys are {}", keys);
                map.get(orientation).get(program).add(keys);
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
        log.info("dataMap: {}", gson.toJson(dataMap));

        for (Map.Entry<String, Map<String, List<Map<String, String>>>> orientations : dataMap.entrySet()) {
            for (Map.Entry<String, List<Map<String, String>>> programs : orientations.getValue().entrySet()) {
                for (Map<String, String> keys : programs.getValue()) {
                    List<String> dat = new ArrayList<>(Collections.nCopies(42, "?"));
                    insertAtIndex(dat,30 , orientations.getKey());
                    insertAtIndex(dat,32, programs.getKey());
//                    log.info("keys {}, {}, {}, {}, {}, {}", keys.get("Ф.И.О. выпускника"), keys.get("№ студ. билета"), keys.get("Тема ВКР"), keys.get("Ученая степень, должность руководителя ВКР"), keys.get("Руководитель ВКР"), keys.get("Гражданство"));
                    insertAtIndex(dat,0, String.valueOf(size));
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
        log.info("dataMap: {}", gson.toJson(data));
        log.info("Ended processing data");
        return data;
    }

    private void insertAtIndex(List<String> list, int index, String value){
        while (list.size() <= index) {
            list.add(" ");
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
            log.info("Generate File is null");
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
        log.info("Size of array is {}", data.get(0).size());

        try {
            List<NiceXWPFDocument> documents = new ArrayList<>();
            log.info("Before generating the document");
            for (int i = 0; i < data.size(); i++) {
                List<String> arr = data.get(i);
                Map<String, Object> dataMap = getStringObjectMap(arr);

                log.info("The dataMap contains: {}", dataMap);
                log.info("Before Saving");
                try {
                    saveDataAsync(dataMap);
                } catch (RestClientException e){
                    log.warn("RestClientException: {}", e.getMessage());
                } catch (Exception e) {
                    log.warn("Async Exception: {}", e.getMessage());
                }
                log.info("After Saving");
                NiceXWPFDocument tempDoc = XWPFTemplate.compile(fileTemplate, Configure.createDefault())
                        .render(dataMap)
                        .getXWPFDocument();

                addPageBreak(tempDoc, i, data.size());
                documents.add(tempDoc);
            }


            document = documents.getLast();
            documents.removeLast();
            document = document.merge(documents, document.getParagraphArray(0).getRuns().getFirst());

            log.info("Closing the documents list");
            for (NiceXWPFDocument doc : documents) {
                doc.close();
            }
            log.info("Done closing the documents list");

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
        log.info("started processing data for id {}; {}", arr.get(0), arr);
        Map<String, Object> dataMap = new HashMap<>();

        Long studNumber = (long) Double.parseDouble(arr.get(2));
//        log.info("studNumber: {}", studNumber);
        dataMap.put("ID", arr.get(0).isEmpty() ? " " : (int) Float.parseFloat(arr.get(0)));
//        log.info("ID: {}", dataMap.get("ID"));
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
        dataMap.put("IndividualOpinion", checkArrayBeforeInserting(arr, 20));
        dataMap.put("Language", checkArrayBeforeInserting(arr, 24));

        dataMap.putIfAbsent("Department", " ");
        dataMap.putIfAbsent("Orientation", " ");
        log.info("Before transferring REST GET method");
        Map<String, String> map = new HashMap<>();
        String departmentName = "?";
        String orientationCodeWithName = "?";


        try {
            log.info("After transferring REST GET method");
            map = (Map<String, String>) restTemplate.getForObject(sqlLocation + "/api/v1/get-department-and-orientation/" + studNumber, Map.class);
            departmentName = map != null ? map.get("Department") : null;
            orientationCodeWithName = map != null ? map.get("Orientation") : null;
            log.info("After transfering REST GET method with map {}", map);
        } catch (RestClientException e){
            log.warn("RestClientException: {}", e.getMessage());
        }

        log.info("DeparmentName and orientationCodeWithName: {}; {}", departmentName, orientationCodeWithName);
        if (departmentName != null && !departmentName.equals("?") && !departmentName.isEmpty()) {
            dataMap.put("Department", departmentName);
        } else if (arr.size() > 29 && !arr.get(29).equals("?")) {
              dataMap.put("Department", arr.get(29));
        } else {
            dataMap.put("Department", " ");
        }
        log.info("Department is {}", dataMap.get("Department"));

        if (orientationCodeWithName != null && !orientationCodeWithName.equals("?") && !orientationCodeWithName.isEmpty()) {
            dataMap.put("Orientation", orientationCodeWithName);
        } else if (arr.size() > 30 && !arr.get(30).equals("?")) {
            dataMap.put("Orientation", arr.get(30));
        } else {
            dataMap.put("Orientation", " ");
        }
        log.info("Orientation is {}", dataMap.get("Orientation"));

        if (arr.size() >= 32 && arr.get(32) != null && !arr.get(32).isEmpty()){
            dataMap.put("Program", arr.get(32));
        }
        dataMap.put("Answer1", " ");
        dataMap.put("Answer2", " ");
        dataMap.put("Answer3", " ");
        String score = checkArrayBeforeInserting(arr, 21);

        String finalScore = "";
        long scoreNumber;
        log.info("score: {}", score );
        if (score != null && !score.contains("?") && !score.contains(" ") ) {
            scoreNumber = (long) Double.parseDouble(score.substring(0, 3).trim()); // First 3 numbers, if it will not happen, fix it

            log.info("scoreNumber: {}", scoreNumber);
            if (scoreNumber < 51L){
                finalScore = scoreNumber + "|E|Плохо";
                dataMap.put("Estimation", "Плохо");
            } else if (scoreNumber < 69L){
                finalScore = scoreNumber + "|D|Удовлетворительно";
                dataMap.put("Estimation", "Удовлетворительно");
            } else if (scoreNumber < 85L){
                finalScore = scoreNumber + "|C|Хорошо";
                dataMap.put("Estimation", "Хорошо");
            } else {
                if (scoreNumber < 95){
                    finalScore = scoreNumber + "|B|Отлично";
                } else {
                    finalScore = scoreNumber + "|A|Отлично";
                }
                dataMap.put("Estimation", "Отлично");
            }
        } else {
            dataMap.put("Estimation", " ");
        }

        dataMap.put("Score", finalScore);

        log.info("Ended processing score: {}", score);
        dataMap.put("IndividualOpinion", " ");

        return dataMap;
    }

//    @Async("asyncTaskExecutor")
    @Override
    public void saveDataAsync(Map<String, Object> dataMap) {
        log.info("In saving data");
        String studentNumber = String.valueOf(dataMap.get("StudNum"));

        log.info("Tring to save data with studentNumber: ", studentNumber);
        ResponseEntity<?> responseEntity = restTemplate.postForEntity(sqlLocation + "/api/v1/save-data", dataMap, ResponseEntity.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Saving the dataMap with student number {} is {}", studentNumber, responseEntity.getBody());
        }
    }

    private String checkArrayBeforeInserting(List<String> arr, int index) {
        if (index < arr.size()) {
            if (arr.get(index).equals("?")) { return " "; }
            return arr.get(index).isEmpty() ? " " : arr.get(index);
        } else {
            log.warn("Index {} is out of bounds for array: {}", index, arr);
            return "?";
        }
    }
}
