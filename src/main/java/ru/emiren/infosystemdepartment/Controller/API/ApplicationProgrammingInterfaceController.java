package ru.emiren.infosystemdepartment.Controller.API;

import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.emiren.infosystemdepartment.Repository.SQL.LecturerRepository;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApplicationProgrammingInterfaceController {
    // todo make json getter for models
    // todo make api for ajax
    private final LecturerRepository lecturerRepository;
    private final StudentService studentService;
    private final DepartmentService departmentService;
    private final LecturerService lecturerService;
    private final OrientationService orientationService;
    private final ProtectionService protectionService;
    private final StudentLecturersService studentLecturersService;
    private final FQWService fqwService;
    private final YearStudentService yearStudentService;

    private final WordService wordService;


    public enum FileTypes{
        Excel(".xslx"),
        Word(".doc"),
        PDF(".pdf"),
        DOCX(".docx");

        private final String fileType;
        FileTypes(String fileType) {
            this.fileType = fileType;
        }
    }

    public enum FileData{
        FQW("fqw"), // FinalQualifyingWork ВКР
        GOV("gos"), //
        REV("rev"), // Review Отзыв
        COM("com"),
        PRO("pro");

        private final String fileData;
        FileData(String fileData) { this.fileData = fileData; }
    }

    private final Map<String, Map<String,List<FileHeader>>> map = new HashMap<>(
            Map.of(
                    FileData.FQW.fileData, new HashMap<>(Map.of(FileTypes.Word.fileType, new ArrayList<>(), FileTypes.Excel.fileType, new ArrayList<>())),
                    FileData.GOV.fileData, new HashMap<>(Map.of(FileTypes.Word.fileType, new ArrayList<>(), FileTypes.Excel.fileType, new ArrayList<>())),
                    FileData.REV.fileData, new HashMap<>(Map.of(FileTypes.Word.fileType, new ArrayList<>(), FileTypes.Excel.fileType, new ArrayList<>())),
                    FileData.COM.fileData, new HashMap<>(Map.of(FileTypes.Word.fileType, new ArrayList<>(), FileTypes.Excel.fileType, new ArrayList<>())),
                    FileData.PRO.fileData, new HashMap<>(Map.of(FileTypes.PDF.fileType , new ArrayList<>()))
            )
    );


    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
    private Date date;

    List<List<String>> data;

    @Autowired
    public ApplicationProgrammingInterfaceController(StudentService studentService,
                                                     DepartmentService departmentService,
                                                     LecturerService lecturerService,
                                                     OrientationService orientationService,
                                                     ProtectionService protectionService,
                                                     StudentLecturersService studentLecturersService,
                                                     LecturerRepository lecturerRepository,
                                                     FQWService fqwService,
                                                     YearStudentService yearStudentService,
                                                     WordService wordService) {
        this.studentService = studentService;
        this.departmentService = departmentService;
        this.lecturerService = lecturerService;
        this.orientationService = orientationService;
        this.protectionService = protectionService;
        this.studentLecturersService = studentLecturersService;
        this.lecturerRepository = lecturerRepository;
        this.fqwService = fqwService;
        this.wordService = wordService;
        this.yearStudentService = yearStudentService;

    }

    @GetMapping("/v1/download_protocols")
    public String downloadProtocols(HttpServletResponse response) throws IOException {
        OutputStream out;
        BufferedOutputStream bos;
        if (!data.isEmpty()) {
            try {
                NiceXWPFDocument doc = wordService.generateWordDocument(data);
                out = response.getOutputStream();
                bos = new BufferedOutputStream(out);
                date = new Date();
                response.setContentType("application/octet-stream");
                response.setHeader("Content-disposition", "attachment;filename=\"" + "protocols-" + dateFormat.format(date) + FileTypes.DOCX.fileType + "\"");
                doc.write(bos);
                bos.flush();
                out.flush();
                PoitlIOUtils.closeQuietlyMulti(doc, bos, out);
                date = null;
            } catch (IllegalStateException e) {
                log.info("Logs for WORD Templating");
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
        return "redirect:/functions";
    }

    @PostMapping("/v1/deserialization-data")
    public String deserialization(MultipartHttpServletRequest request, RedirectAttributes red) throws IOException {
        MultipartFile file = request.getFile("protocol");

        if (file == null || !file.getOriginalFilename().endsWith(FileTypes.DOCX.fileType) || file.isEmpty()) {
            log.info("Something Wrong With File");
        }

        if (file != null && file.getSize() > 0) {
            data = getListOfDataFromFile(file.getInputStream());
        }
        return "redirect:/functions";
    }

    private List<List<String>> getListOfDataFromFile(InputStream file) {
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

    @PostMapping("/v1/upload-data")
    public ResponseEntity<String> uploadDataAndProceedToModels(MultipartHttpServletRequest request, RedirectAttributes red) throws IOException {
        MultipartFile file = request.getFile("data");

        return ResponseEntity.status(HttpStatus.OK).body("Proceed");
    }


//    @PostMapping("/v1/upload-zip-file")
    public ResponseEntity<String> uploadZipFile(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
        MultipartFile file = request.getFile("zip");
        assert file != null;
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        log.info(file.toString());

        try {
            File tempFile = File.createTempFile("tempZip", ".zip");
            file.transferTo(tempFile);
            ZipFile zipFile = new ZipFile(tempFile);
            zipFile.setCharset(Charset.forName("CP866"));

            List<FileHeader> fileHeaders = zipFile.getFileHeaders();

            for (FileHeader fileHeader : fileHeaders) {
                String fileName = fileHeader.getFileName();
                if (!fileHeader.isDirectory() && ( fileName.endsWith(FileTypes.DOCX.fileType) || fileName.endsWith(FileTypes.Excel.fileType) || fileName.endsWith(FileTypes.PDF.fileType)) ) {
                    log.info(fileName);

                    if (fileName.contains("ВКР")) {
                        if (fileName.endsWith(FileTypes.DOCX.fileType)){
                            map.get(FileData.FQW.fileData).get(FileTypes.Word.fileType).add(fileHeader);
                        } else if (fileName.endsWith(FileTypes.Excel.fileType)) {
                            map.get(FileData.FQW.fileData).get(FileTypes.Excel.fileType).add(fileHeader);
                        }

                    } else if (fileName.contains("ГОС")) {
                        log.info("ГОС " + fileName);
                        if (fileName.endsWith(FileTypes.DOCX.fileType)){
                            map.get(FileData.GOV.fileData).get(FileTypes.Word.fileType).add(fileHeader);
                        } else if (fileName.endsWith(FileTypes.Excel.fileType)) {
                            map.get(FileData.GOV.fileData).get(FileTypes.Excel.fileType).add(fileHeader);
                        }

                    } else if (fileName.contains("Отзыв и рец/")) {
                        log.info("Отзыв и рец " + fileName);
                        if (fileName.endsWith(FileTypes.DOCX.fileType)){
                            map.get(FileData.REV.fileData).get(FileTypes.Word.fileType).add(fileHeader);
                        } else if (fileName.endsWith(FileTypes.Excel.fileType)) {
                            map.get(FileData.REV.fileData).get(FileTypes.Excel.fileType).add(fileHeader);

                        }

                    } else if (fileName.contains("Отчёт председателя/")) {
                        log.info("отчёт председателя " + fileName);
                        if (fileName.endsWith(FileTypes.DOCX.fileType)){
                            map.get(FileData.COM.fileData).get(FileTypes.Word.fileType).add(fileHeader);
                        } else if (fileName.endsWith(FileTypes.Excel.fileType)) {
                            map.get(FileData.COM.fileData).get(FileTypes.Excel.fileType).add(fileHeader);
                        }
                    } else if (fileName.contains("Приказы/") && fileName.endsWith(FileTypes.PDF.fileType)){
                            map.get(FileData.PRO.fileData).get(FileTypes.PDF.fileType).add(fileHeader);
                    }
                }
            }


//            handleFqwWordFile(zipFile, map.get(FileData.FQW.fileData).get(FileTypes.Word.fileType));
//            handleFqwExcelFile(zipFile, map.get(FileData.FQW.fileData).get(FileTypes.Excel.fileType));
//            handleGosWordFile(zipFile, map.get(FileData.GOV.fileData).get(FileTypes.Word.fileType));
//            handleGosExcelFile(zipFile, map.get(FileData.GOV.fileData).get(FileTypes.Excel.fileType));
//            handleReviewWordFile(zipFile, map.get(FileData.REV.fileData).get(FileTypes.Word.fileType));
//            handleReviewExcelFile(zipFile, map.get(FileData.REV.fileData).get(FileTypes.Excel.fileType));
//            handleCommisionersReviewWordFile(zipFile, map.get(FileData.COM.fileData).get(FileTypes.Word.fileType));
//            handleCommisionersReviewExcelFile(zipFile, map.get(FileData.COM.fileData).get(FileTypes.Excel.fileType));
//            handleDecreePdfFile(zipFile, map.get(FileData.PRO.fileData).get(FileTypes.PDF.fileType));

        } catch (IOException ex ) {
            log.warn(ex.getMessage());
        } finally {
            map.clear();
        }

        return null;
    }

    private final Map<String, Map<String, List<String>>> mapOfDataFromPDF = new HashMap<>();

    private void handleDecreePdfFile(ZipFile zipFile, List<FileHeader> fileHeaders) throws IOException, InterruptedException {


        final Pattern NUMERIC_PATTERN = Pattern.compile("^(0|[1-9]\\d*)$");
        Map<String, List<String>> listMap = new HashMap<>();
        List<String> currentHeaders = new ArrayList<>();

        for (FileHeader fileHeader : fileHeaders) {
            log.info("fileHeader " + fileHeader.getFileName());
            byte[] bytes = zipFile.getInputStream(fileHeader).readAllBytes();
            PDDocument document = Loader.loadPDF(bytes);

//            PDFTextStripper reader = new PDFTextStripper();

            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            PageIterator pi = new ObjectExtractor(document).extract();
            String orientation;
            String text = null;

            while (pi.hasNext()) {
                Page page = pi.next();
                List<Table> tables = sea.extract(page);
                for (Table table : tables) {
                    boolean isHeaderProceed = false;
                    String nonEmptyCellsString = "";
                    for (List<RectangularTextContainer> rows : table.getRows()) {
                        int nonEmptyCells = 0;
                        int numericCells = 0;
                        for (RectangularTextContainer col : rows) {
                            text = col.getText().replaceAll("\r", " ").trim();
                            log.info(text);

                            if (text.contains("No п/п")){
                                break;
                            }
                            if (NUMERIC_PATTERN.matcher(text).matches()) {
                                log.warn("text is numeric");
                                numericCells++;
                            }
                            if (numericCells > 1){
                                log.warn("skipping in numeric cells");
                                break;
                            }

                            if (!isHeaderProceed){
                                if (text.isEmpty()){
                                    nonEmptyCells++;

                                }
                                if (nonEmptyCells > 1) {
                                    isHeaderProceed = true;
                                    nonEmptyCells = 0;
                                    continue;
                                }
                            }

                            log.info("nonEmptyCells: " + nonEmptyCells);
                        }

                        Thread.sleep(1000);


                        for (RectangularTextContainer cell : rows){
                            text = cell.getText().replaceAll("\r", " ").trim();
                        }


                    }
                }
            }
//            boolean flag = false;
//            for (int i = 1; i < pageCount; i++) {
//                reader.setStartPage(i);
//                reader.setEndPage(i);
//                String pageText = reader.getText(document).replaceAll("\\s+", " ").trim();
//                log.info("useless" + pageText);
//                if (!flag && !pageText.contains(keyword)){
//                    flag = true;
//                    continue;
//                }



//                log.info("Page " + i + ": " + pageText);
//                Thread.sleep(10000);
//            }

            document.close();
        }
    }

//    private void handleFqwExcelFile(ZipFile zipFile, List<FileHeader> l) {
//        for (FileHeader fileHeader : l) {
//            try {
//                data = getListOfDataFromFile(zipFile.getInputStream(fileHeader));
//            } catch (IOException e) {
//                log.warn(e.getMessage());
//            }
//        }
//    }

    private void handleFqwWordFile(ZipFile zipFile, List<FileHeader> l) {
        zipFile.setCharset(Charset.forName("CP866"));
        for (FileHeader fileHeader : l) {
            if (fileHeader.getFileName().contains("Таблица_защит")) {
                try {
                    data = getListOfDataFromFile(zipFile.getInputStream(fileHeader));
                    parseDataFromWordToSqlDatabase(data);
                } catch (IOException e) {
                    log.warn(e.getMessage());
                }
            }
        }
    }

    private void parseDataFromWordToSqlDatabase(List<List<String>> data) {
        // I am not sure about liquidity of data
        for (List<String> row : data) {
            int Num = Integer.parseInt(row.get(0));
//            String nameInRod = row.get(1);
            long studNum = Long.parseLong(row.get(2));
            String theme = row.get(3);
            String jobAndPost = row.get(4);
            String SupervisionsName = row.get(5);
            String consRP = row.get(6); //idk what it is
            String Reviewer = row.get(7); //Рец-нт?
            String reviewersJobAndPost = row.get(8);
            String C = row.get(9);
            // second Num = 10
            String questioner1 = row.get(11);
            String question1 = row.get(12);
            String questioner2 = row.get(13);
            String question2 = row.get(14);
            String questioner3 = row.get(15);
            String question3 = row.get(16);
            // third num = 17
            int score = Integer.parseInt(row.get(18)); // Оценка
            String name = row.get(19); // ФИО студента без род.пад
            String SpecialOpinions = row.get(20);
            // Score Rus + Euro Score = 21
            // Start Time of FQW = 22
            // Length of FQW in time = 23
            String language = row.get(24);




        }
    }
}


//                addStudentToDb();
//                addLecturersToDb();
//                addFqwToDb();
//                addYearDepToDb();
//                addProtocolToDb();
//                addReviewerToDb();
//                addProtectionToDb();
//                addOrientationToDb();
//                addDepartmentsToDb();
//                addCommisionerToDb();

