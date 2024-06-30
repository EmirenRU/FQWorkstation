package ru.emiren.infosystemdepartment.Controller.API;

import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.emiren.infosystemdepartment.Repository.SQL.LecturerRepository;
import ru.emiren.infosystemdepartment.Repository.SQL.YearRepository;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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


    private final RestTemplate restTemplate = new RestTemplate();
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
    public void downloadProtocols(HttpServletResponse response) throws IOException {
        OutputStream out;
        BufferedOutputStream bos;
        if (!data.isEmpty()) {
            try {
                NiceXWPFDocument doc = wordService.generateWordDocument(data);
                out = response.getOutputStream();
                bos = new BufferedOutputStream(out);
                date = new Date();
                response.setContentType("application/octet-stream");
                response.setHeader("Content-disposition", "attachment;filename=\"" + "protocols-" + dateFormat.format(date) + ".docx" + "\"");
                doc.write(bos);
                bos.flush();
                out.flush();
                PoitlIOUtils.closeQuietlyMulti(doc, bos, out);
                date = null;
            } catch (IllegalStateException e) {
                System.out.println("Logs for WORD Templating");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @PostMapping("/v1/deserialization-data")
    public void deserialization(@RequestParam("file") MultipartFile file) {
        if (data == null){
            data = new ArrayList<>();
        }

        if (!data.isEmpty()){
            for (List<String> row : data)
                row.clear();
            data.clear();
        }

        if (!file.isEmpty()){
            try {
                data = new ArrayList<>();
                NiceXWPFDocument document = new NiceXWPFDocument(file.getInputStream());
                List<XWPFTable> tables = document.getTables();

                if (tables.size() == 3){
                    XWPFTable t1 = tables.get(0);
                    XWPFTable t2 = tables.get(1);
                    XWPFTable t3 = tables.get(2);

                    for (int i = 1 ; i < t1.getNumberOfRows() ; i++){
                        XWPFTableRow r1 = t1.getRow(i);
                        XWPFTableRow r2 = t2.getRow(i);
                        XWPFTableRow r3 = t3.getRow(i);

                        List<String> innerArray = new ArrayList<>() {};
                        innerArray.addAll(wordService.processTable(t1, i, r1.getTableCells().size()));
                        innerArray.addAll(wordService.processTable(t2, i, r2.getTableCells().size()));
                        innerArray.addAll(wordService.processTable(t3, i, r3.getTableCells().size()));

                        data.add(innerArray);
                        }
                    }
                } catch (IOException e) {
                System.out.println("Handle later deserialization");
            }
        }


    }

}
