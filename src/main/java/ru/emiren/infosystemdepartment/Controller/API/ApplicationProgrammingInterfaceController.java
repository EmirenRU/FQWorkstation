package ru.emiren.infosystemdepartment.Controller.API;

import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/v2/upload_file")
    public String uploadFileToCheckAndReturnFile(@RequestParam("file") MultipartFile request){
        log.info("In uploadFileToCheckAndReturnFile");
        String name = request.getOriginalFilename();

        if (name != null && name.contains(".")){
            String fileFormat = name.substring(name.lastIndexOf(".") + 1);
            if (fileFormat.equals("xlsx") || fileFormat.equals("xls")) {
                log.info("IN xlsx|xls comparison");

            } else if (fileFormat.equals("doc") || fileFormat.equals("docx")) {
                log.info("IN docx|doc comparison");

            }
            log.info("Working with file with format " +  fileFormat);
        }


        log.info("Out uploadFileToCheckAndReturnFile: " + name);
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
    public String deserialization(MultipartHttpServletRequest request) throws IOException {
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

