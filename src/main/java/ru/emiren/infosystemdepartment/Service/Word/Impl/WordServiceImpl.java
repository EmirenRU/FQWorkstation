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
    public NiceXWPFDocument generateWordDocument() throws Exception{
        studentLecturersDTO = studentLecturersService.getAllStudentLecturers();
        protectionsDTO = protectionService.getAllProtections();
        yearStudentsDTO = yearStudentService.getAllYearStudent();
        orientationsDTO = orientationService.getAllOrientations();
        NiceXWPFDocument doc = createWordDocumentByTemplatesPath(studentLecturersDTO, protectionsDTO, yearStudentsDTO, orientationsDTO);
        return doc;
    }

    private XWPFDocument createWordDocument() {
        return new XWPFDocument();
    }

    private NiceXWPFDocument createWordDocumentByTemplatesPath(List<StudentLecturersDTO> studentLecturers,
                                                               List<ProtectionDTO> protections,
                                                               List<YearStudentDTO> yearStudents,
                                                               List<OrientationDTO> orientationsDTO)
            throws Exception
    {
        try {
            filePath = ResourceUtils.getFile("classpath:template.docx");

            NiceXWPFDocument source = new NiceXWPFDocument(new FileInputStream(filePath));
            List<NiceXWPFDocument> documents = new ArrayList<>();

            for (int i = 0; i < studentLecturers.size(); i++) {
                StudentLecturersDTO sl = studentLecturers.get(i);
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("id", i+1);
                LocalDate date = protectionService.getDateWithSpecificStudent(sl, protections);
                dataMap.put("date", date);
                dataMap.put("orientationCode", sl.getStudent().getOrientation().getCode());
                dataMap.put("orientationName", sl.getStudent().getOrientation().getName());
                dataMap.put("studentName", sl.getStudent().getFio());
                dataMap.put("theme", sl.getStudent().getFqw().getName());
                dataMap.put("departmentName", sl.getStudent().getDepartment().getName());
                dataMap.put("lecturerAcademicDegree", sl.getLecturer().getAcademicDegree());
                dataMap.put("lecturerName", sl.getLecturer().getFio());
                dataMap.put("lecturerPos", sl.getLecturer().getPosition());
                dataMap.put("reviewerAD","?");
                dataMap.put("reviewerPos","?");
                dataMap.put("reviewerName","?");
                dataMap.put("consultantAD","?");
                dataMap.put("consultantPos","?");
                dataMap.put("consultantName","?");

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
