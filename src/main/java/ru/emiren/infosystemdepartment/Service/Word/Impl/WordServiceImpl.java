package ru.emiren.infosystemdepartment.Service.Word.Impl;

import com.deepoove.poi.XWPFTemplate;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;
import ru.emiren.infosystemdepartment.Model.SQL.YearStudent;
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
    public XWPFDocument generateWordDocument() {
        studentLecturersDTO = studentLecturersService.getAllStudentLecturers();
        protectionsDTO = protectionService.getAllProtections();
        yearStudentsDTO = yearStudentService.getAllYearStudent();
        orientationsDTO = orientationService.getAllOrientations();
        XWPFDocument doc = createWordDocumentByTemplatesPath(studentLecturersDTO, protectionsDTO, yearStudentsDTO, orientationsDTO);
        return doc;
    }

    private XWPFDocument createWordDocument() {
        return new XWPFDocument();
    }

    private XWPFDocument createWordDocumentByTemplatesPath(List<StudentLecturersDTO> studentLecturers, List<ProtectionDTO> protections, List<YearStudentDTO> yearStudents, List<OrientationDTO> orientationsDTO) {
        try {
            filePath = ResourceUtils.getFile("classpath:template.docx");

            XWPFDocument finalDocument = new XWPFDocument();

            for (int i = 0; i < studentLecturers.size(); i++) {
                StudentLecturersDTO sl = studentLecturers.get(i);
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("id", i);
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


                XWPFTemplate tempTemplate = XWPFTemplate.compile(filePath).render(dataMap);
                XWPFDocument tempDoc = tempTemplate.getXWPFDocument();
//
//                for (int j = 0; j < tempDoc.getParagraphs().size(); j++){
//                    tempDoc.getParagraph().setStyle();
//                }

                tempDoc.getParagraphs().forEach(p -> finalDocument.createParagraph().createRun().setText(p.getText()));

                finalDocument.createParagraph().createRun().addBreak();

                tempTemplate.close();
            }
            return finalDocument;
        } catch (IOException e) {
            System.out.println("Something wrong with Doc");
        }
        return null;
    }
}
