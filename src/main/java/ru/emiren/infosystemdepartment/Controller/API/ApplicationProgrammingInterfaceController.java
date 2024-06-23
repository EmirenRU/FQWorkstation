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
import java.time.LocalDate;
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
    private final WordService wordService;
    private final YearRepository yearRepository;

    private final RestTemplate restTemplate = new RestTemplate();

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
                                                     YearRepository yearRepository,
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
        this.yearRepository = yearRepository;
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
                response.setContentType("application/octet-stream");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
                Date date = new Date();
                response.setHeader("Content-disposition", "attachment;filename=\"" + "protocols-" + dateFormat.format(date) + ".docx" + "\"");
                doc.write(bos);
                bos.flush();
                out.flush();
                PoitlIOUtils.closeQuietlyMulti(doc, bos, out);
            } catch (IllegalStateException e) {
                System.out.println("Logs for WORD Templating");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping("/v1/refresh-db")
    public void refreshDb() {

    }

    @PostMapping("/v1/deserialization-data")
    public void deserialization(@RequestParam("file") MultipartFile file) {
        System.out.println("i am here");
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
                System.out.println(document);

                List<XWPFTable> tables = document.getTables();
                System.out.println(tables.size());
                if (tables.size() == 3){
                    XWPFTable t1 = tables.get(0);
                    XWPFTable t2 = tables.get(1);
                    XWPFTable t3 = tables.get(2);


                    int numRows = t1.getNumberOfRows();
                    for (int i = 1 ; i < numRows ; i++){

                        XWPFTableRow r1 = t1.getRow(i);
                        XWPFTableRow r2 = t2.getRow(i);
                        XWPFTableRow r3 = t3.getRow(i);

                        int numCells1 = r1.getTableCells().size();
                        int numCells2 = r2.getTableCells().size();
                        int numCells3 = r3.getTableCells().size();

                        List<String> innerArray = new ArrayList<>() {};
                        innerArray.addAll(wordService.processTable(t1, i, numCells1));
                        innerArray.addAll(wordService.processTable(t2, i, numCells2));
                        innerArray.addAll(wordService.processTable(t3, i, numCells3));

                        data.add(innerArray);


                        }
                    }
                } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

}
