package ru.emiren.infosystemdepartment.Service.Word.Impl;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;
import ru.emiren.infosystemdepartment.Model.SQL.YearStudent;
import ru.emiren.infosystemdepartment.Repository.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class WordServiceImpl implements WordService {
    private StudentRepository studentRepository;
    private StudentLecturerRepository studentLecturerRepository;
    private LecturerRepository lecturerRepository;
    private ProtectionRepository protectionRepository;
    private YearStudentRepository yearStudentRepository;
    private OrientationRepository orientationRepository;

    @Autowired
    public WordServiceImpl(StudentRepository studentRepository,
                           StudentLecturerRepository studentLecturerRepository,
                           LecturerRepository lecturerRepository,
                           ProtectionRepository protectionRepository,
                           YearStudentRepository yearStudentRepository,
                           OrientationRepository orientationRepository)
    {
        this.studentRepository = studentRepository;
        this.studentLecturerRepository = studentLecturerRepository;
        this.lecturerRepository = lecturerRepository;
        this.protectionRepository = protectionRepository;
        this.yearStudentRepository = yearStudentRepository;
        this.orientationRepository = orientationRepository;

    }

    @Override
    public XWPFDocument generateWordDocument() {
        List<StudentLecturers> studentLecturers = studentLecturerRepository.findAll();
        List<Protection> protections = protectionRepository.findAll();
        List<YearStudent> yearStudents = yearStudentRepository.findAll();
        XWPFDocument doc = createWordDocumentByTemplatesPath();
        populateWordDocument(doc, studentLecturers, protections, yearStudents);
        return doc;
    }

    private XWPFDocument createWordDocument() {
        return new XWPFDocument();
    }

    private XWPFDocument createWordDocumentByTemplatesPath() {
        try {
            // Load the existing Word document (template)
//            File file = ResourceUtils.getFile("classpath:template.docx");
//            XWPFDocument oldDoc = new XWPFDocument(new FileInputStream(file));
//
//            // Create a new XWPFDocument
//            XWPFDocument newDoc = new XWPFDocument(oldDoc.getPackage());
//
//            for (XWPFParagraph paragraph : newDoc.getParagraphs().get()) {
//                String text = paragraph.getText();
//                if (text.contains("“10” июня 2022")){
//                        text.replace("“10” июня 2022", pro)
//                }
//            }

            // Write the new document to a file
            FileOutputStream fos = new FileOutputStream("out.docx");
//            newDoc.write(fos);
            fos.close();


            return newDoc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyParagraph(XWPFParagraph source, XWPFParagraph target) {
        // Copy the text and styles from source to target paragraph
        for (XWPFRun run : source.getRuns()) {
            XWPFRun newRun = target.createRun();
            newRun.setText(run.getText(0));
            newRun.setBold(run.isBold());
            // Copy other styles as needed (italic, underline, etc.)
        }
    }

    private void populateWordDocument(XWPFDocument doc, List<StudentLecturers> studentLecturers,
                                      List<Protection> protections, List<YearStudent> yearStudents) {
//        for (int i = 0; i < studentLecturers.size(); i++) {
//            XWPFParagraph title = doc.createParagraph();
//            title.setAlignment(ParagraphAlignment.CENTER);
//            XWPFRun titleRun = title.createRun();
//            titleRun.setText("Protocol №" + studentLecturers.get(i).getStudent().getId() + "\n")
//
//            XWPFParagraph text = doc.createParagraph();
//            text.setAlignment(ParagraphAlignment.START);
//            XWPFRun textRun = text.createRun();
//            textRun.setText("заседания ");
//            textRun.setBold(true);
//            textRun.setText("Государственной экзаменационной комиссии ");
//            textRun.setBold(false);
//            textRun.setText(protectionRepository.findDateByOrientationCode(studentLecturers.get(i).getStudent().getOrientation().getCode()).getDateOfProtection().toString() + " по рассмотрению\n");
//            textRun.setBold(true);
//            textRun.setText("направлению ");
//            textRun.setBold(false);
//            textRun.setText("(специальности): \n");
//            textRun.setUnderline(UnderlinePatterns.DASH);
//            textRun.setText(studentLecturers.get(i).getStudent().getOrientation().getCode());
//            textRun.addTab();
//            textRun.addTab();
//            textRun.setText(studentLecturers.get(i).getStudent().getOrientation().getName());
//        }
//
//    // O(n*m)
    }
}
