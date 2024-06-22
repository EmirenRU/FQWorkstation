package ru.emiren.infosystemdepartment.Service.Word.Impl;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.util.ResourceUtils;
import com.deepoove.poi.XWPFTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {
    private StudentLecturersService studentLecturersService;
    private StudentService studentService;
    private LecturerService lecturerService;
    private ProtectionService protectionService;
    private YearStudentService yearStudentService;
    private OrientationService orientationService;
    private File filePath;

    List<StudentLecturersDTO> studentLecturersDTO;
    List<ProtectionDTO> protectionsDTO;
    List<YearStudentDTO> yearStudentsDTO;
    List<OrientationDTO> orientationsDTO;
    List<LecturerDTO> lecturersDTO;
    List<StudentDTO> studentsDTO;

    @Autowired
    public WordServiceImpl(StudentService studentService,
                           StudentLecturersService studentLecturersService,
                           LecturerService lecturerService,
                           ProtectionService protectionService,
                           YearStudentService yearStudentService,
                           OrientationService orientationService) {
        this.studentService = studentService;
        this.studentLecturersService = studentLecturersService;
        this.lecturerService = lecturerService;
        this.protectionService = protectionService;
        this.yearStudentService = yearStudentService;
        this.orientationService = orientationService;
    }

    @Override
    public NiceXWPFDocument generateWordDocument(List<List<String>> data) throws Exception{

        NiceXWPFDocument doc = createWordDocumentByTemplatesPath(data);
        return doc;
    }

    @Override
    public List<Map<String, String>> handleUploadFile(NiceXWPFDocument document) throws Exception {
        return List.of();
    }

    @Override
    public List<String> processTable(XWPFTable table, int indexRow, int numCells) {
        XWPFTableRow row = table.getRow(indexRow);
        return row.getTableCells().stream()
                .map(cell -> cell.getText())
                .collect(Collectors.toList());
    }

    private XWPFDocument createWordDocument() {
        return new XWPFDocument();
    }

    private NiceXWPFDocument createWordDocumentByTemplatesPath(List<List<String>> data)
            throws Exception
    {
        try {
            filePath = ResourceUtils.getFile("classpath:temp.docx");

            NiceXWPFDocument source = new NiceXWPFDocument(new FileInputStream(filePath));
            List<NiceXWPFDocument> documents = new ArrayList<>();

            for (List<String> arr : data) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("№01", arr.get(0));
//                dataMap.put("date", LocalDate.now());
//                dataMap.put("orientationCode", "OrientationCode");
//                dataMap.put("orientationName", "orientationName");
                dataMap.put("№02", arr.get(1));
                dataMap.put("№03", arr.get(2));
//                dataMap.put("departmentName", sl.getStudent().getDepartment().getName());
                dataMap.put("№04", arr.get(3));
                dataMap.put("№05", arr.get(4));
                dataMap.put("№06", arr.get(5));
                dataMap.put("№07",arr.get(6));
                dataMap.put("№08", arr.get(7));
                dataMap.put("№09", arr.get(8));
                dataMap.put("№10", arr.get(9));
                dataMap.put("№11", arr.get(11));
                dataMap.put("№12", arr.get(12));
                dataMap.put("№13", arr.get(13));
                dataMap.put("№14", arr.get(14));
                dataMap.put("№15", arr.get(15));
                dataMap.put("№16", arr.get(16));
                dataMap.put("№17", arr.get(18));
                dataMap.put("№18", arr.get(19));
                dataMap.put("№19", arr.get(20));
                dataMap.put("№20", arr.get(21));
                dataMap.put("№21", arr.get(22));
                dataMap.put("№22", arr.get(23));
                dataMap.put("№23", arr.get(24));


                documents.add(XWPFTemplate.compile(filePath).render(dataMap).getXWPFDocument());
            }
            source = source.merge(documents, source.getParagraphArray(0).getRuns().get(0));

            return source;
        } catch (IOException e) {
            System.out.println("Something wrong with Doc");
        }
        return null;
    }


}
