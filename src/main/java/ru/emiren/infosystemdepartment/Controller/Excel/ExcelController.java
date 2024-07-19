package ru.emiren.infosystemdepartment.Controller.Excel;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
@RequestMapping("api/v2")
public class ExcelController {
    private final Integer THREADS;
    private ThreadPoolExecutor threadExecutor;

    private Map<String, String> map;

    private XSSFWorkbook workbook = null;
    private DataFormatter dataFormatter;

    private Map<String, String> listsOfDepartments;
    private Map<String, List<String>> listsOfUrNorms;
    private Map<String, List<String>> listsOfOop;
    private Map<String, String> listsOfInitials;
    private List<List<String>> listsOfConsolitatedUN;


    @Autowired
    public ExcelController(@Value("${number.of.threads}") Integer THREADS) {
        this.THREADS = THREADS;

        map = new HashMap<>();
        threadExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS);

        dataFormatter = new DataFormatter();
    }

    @PostMapping("upload-excel")
    public String uploadExcel(MultipartHttpServletRequest request) {
        MultipartFile file = request.getFile("excel");

        try {
            System.out.println(file.getContentType());
            workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet s1 = workbook.getSheet("УН сводная");
            XSSFSheet s2 = workbook.getSheet("ООП");
            XSSFSheet s3 = workbook.getSheet("Список кафедр");
            XSSFSheet s4 = workbook.getSheet("Нормы УР");
            XSSFSheet s5 = workbook.getSheet("Списки");

            // ToDo make threadPoolExecution
            System.out.println("s4");
            listsOfUrNorms = addUrNormsToMapFromSheet(s4);
            System.out.println("s2");
            listsOfOop = addOopToMapFromSheet(s2);
            System.out.println("s3");
            listsOfDepartments = addDepartmentsToMapFromSheet(s3);
            System.out.println("s5");
            listsOfInitials = addInitialsToMapFromSheet(s5);
            System.out.println("s1");
            listsOfConsolitatedUN = addConsolitatedUnToMatrixFromSheet(s1);
            addDataToDbFromSheets(listsOfConsolitatedUN ,listsOfOop, listsOfDepartments, listsOfUrNorms, listsOfInitials);
            cleanDataFromLists(listsOfConsolitatedUN ,listsOfOop, listsOfDepartments, listsOfUrNorms, listsOfInitials);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/functions";

    }

    private void cleanDataFromLists(List<List<String>> listsOfConsolitatedUN, Map<String, List<String>> listsOfOop, Map<String, String> listsOfDepartments, Map<String, List<String>> listsOfUrNorms, Map<String, String> listsOfInitials) {
        listsOfConsolitatedUN.forEach(List::clear);
        listsOfConsolitatedUN.clear();
        listsOfOop.clear();
        listsOfDepartments.clear();
        listsOfUrNorms.clear();
        listsOfInitials.clear();
    }

    private List<List<String>> addConsolitatedUnToMatrixFromSheet(XSSFSheet consolitatedUN) {
        int minYN = 4;
        int maxYN = consolitatedUN.getLastRowNum();
        List<List<String>> matrix = new ArrayList<>();
        List<String> rowData;
        for (int i = minYN; i <= maxYN; i++) {
            XSSFRow row = consolitatedUN.getRow(i);

            rowData = Arrays.asList(
                    // Students
                    dataFormatter.formatCellValue(row.getCell(22)), // code
                    dataFormatter.formatCellValue(row.getCell(23)), // groupNumber
                    dataFormatter.formatCellValue(row.getCell(24)), // ofGroups
                    dataFormatter.formatCellValue(row.getCell(25)), // subgroups
                    dataFormatter.formatCellValue(row.getCell(26)), // totalPeople
                    dataFormatter.formatCellValue(row.getCell(27)), // rf
                    dataFormatter.formatCellValue(row.getCell(28)), // foreign
                    dataFormatter.formatCellValue(row.getCell(29)), // standard
                    dataFormatter.formatCellValue(row.getCell(30)), // calculated
                    dataFormatter.formatCellValue(row.getCell(31)), // pk

                    // EducationalProgram
                    dataFormatter.formatCellValue(row.getCell(0)), // eduForm
                    dataFormatter.formatCellValue(row.getCell(1)), // levelOp
                    dataFormatter.formatCellValue(row.getCell(2)), // theCodeOfTheOOPRUDN
                    dataFormatter.formatCellValue(row.getCell(3)), // directionCode
                    dataFormatter.formatCellValue(row.getCell(4)), // nameOfTheProgram

                    // DisciplineOrTypeOfAcademicWork
                    dataFormatter.formatCellValue(row.getCell(5)), // block
                    dataFormatter.formatCellValue(row.getCell(6)), // component
                    dataFormatter.formatCellValue(row.getCell(7)), // nvrup
                    dataFormatter.formatCellValue(row.getCell(14)), // laboratoriesHours
                    dataFormatter.formatCellValue(row.getCell(15)), // practiseHours
                    dataFormatter.formatCellValue(row.getCell(16)), // typeOfPaOrGia
                    dataFormatter.formatCellValue(row.getCell(17)), // courseWorks
                    dataFormatter.formatCellValue(row.getCell(18)), // courseProjects
                    dataFormatter.formatCellValue(row.getCell(19)), // courseUchAveZeOnRup
                    dataFormatter.formatCellValue(row.getCell(20)), // prZeOnRup
                    dataFormatter.formatCellValue(row.getCell(21)), // nirZeByRup
                    dataFormatter.formatCellValue(row.getCell(9)),  // nameof
                    dataFormatter.formatCellValue(row.getCell(8)),  // dopinfo

                    // ReadKW
                    dataFormatter.formatCellValue(row.getCell(10)), // semesterOrModule
                    dataFormatter.formatCellValue(row.getCell(11)), // weeksPerSemesterModule
                    dataFormatter.formatCellValue(row.getCell(12)), // typeOfEducationalWork
                    dataFormatter.formatCellValue(row.getCell(13)), // lectureHours

                    // InformationAboutPps
                    dataFormatter.formatCellValue(row.getCell(32)), // department
                    dataFormatter.formatCellValue(row.getCell(33)), // post
                    dataFormatter.formatCellValue(row.getCell(34)), // termsOfAttraction
                    dataFormatter.formatCellValue(row.getCell(35)), // fullName
                    dataFormatter.formatCellValue(row.getCell(36)), // aSpecialFeature

                    // TheAmountOfTeachingWorkOfTheTeachingStaff
                    dataFormatter.formatCellValue(row.getCell(37)), // lectures
                    dataFormatter.formatCellValue(row.getCell(38)), // practiceOrSeminars
                    dataFormatter.formatCellValue(row.getCell(39)), // labWorksOrClinicalClasses
                    dataFormatter.formatCellValue(row.getCell(40)), // currentControl
                    dataFormatter.formatCellValue(row.getCell(41)), // interimCertificationPoForBrs
                    dataFormatter.formatCellValue(row.getCell(42)), // registrationOfPaResults
                    dataFormatter.formatCellValue(row.getCell(43)), // ongoingConsultationsOnTheDiscipline
                    dataFormatter.formatCellValue(row.getCell(44)), // courseWorksAmount
                    dataFormatter.formatCellValue(row.getCell(45)), // courseProjectsAmount
                    dataFormatter.formatCellValue(row.getCell(46)), // educationalPractice
                    dataFormatter.formatCellValue(row.getCell(47)), // procPedagogicalAndPreGraduatePractices
                    dataFormatter.formatCellValue(row.getCell(48)), // nir
                    dataFormatter.formatCellValue(row.getCell(49)), // practicesIncludingResearchOfDigitalMagistracies
                    dataFormatter.formatCellValue(row.getCell(50)), // reviewingTheAbstractsOfGraduateStudents
                    dataFormatter.formatCellValue(row.getCell(51)), // candidatesExams
                    dataFormatter.formatCellValue(row.getCell(52)), // scientificGuidance
                    dataFormatter.formatCellValue(row.getCell(53)), // theLeadershipOfTheWrcOrTheNkr
                    dataFormatter.formatCellValue(row.getCell(54)), // reviewOfTheWrc
                    dataFormatter.formatCellValue(row.getCell(55)), // gek
                    dataFormatter.formatCellValue(row.getCell(56)), // total

                    // Semesters
                    dataFormatter.formatCellValue(row.getCell(58)), // auditionWork
                    dataFormatter.formatCellValue(row.getCell(59)), // pairsPerWeek
                    dataFormatter.formatCellValue(row.getCell(60))  // activities
            );
            matrix.add(rowData);
        }
        return matrix;
    }

    private void addDataToDbFromSheets(List<List<String>> listOfConsolitatedUN, Map<String, List<String>> listsOfOop,  Map<String, String> listsOfDepartments, Map<String, List<String>> listsOfUrNorms, Map<String, String> listsOfInitials) {
            // Students
//            String code = row.getCell(22).getStringCellValue();        // w
//            String groupNumber = row.getCell(23).getStringCellValue(); // x
//            String ofGroups = row.getCell(24).getStringCellValue();    // y
//            String subgroups = row.getCell(25).getStringCellValue();   // z
//            String totalPeople = row.getCell(26).getStringCellValue(); // aa
//            String rf = row.getCell(27).getStringCellValue();          // ab
//            String foreign = row.getCell(28).getStringCellValue();     // ac
//            String standart = row.getCell(29).getStringCellValue();    // ad
//            String calculated = row.getCell(30).getStringCellValue();  // ae
//            String pk = row.getCell(31).getStringCellValue();          // af
//
//            // EducationalProgram
//            String eduForm = row.getCell(0).getStringCellValue();      // a
//            String levelOp = row.getCell(1).getStringCellValue();      // b
//            String theCodeOfTheOOPRUDN = row.getCell(2).getStringCellValue(); // c
//            String directionCode = row.getCell(3).getStringCellValue();       // d
//            String nameOfTheProgram = row.getCell(4).getStringCellValue();    // e
//
//            // DisciplineOrTypeOfAcademicWork
//            String block = row.getCell(5).getStringCellValue();        // f
//            String component = row.getCell(6).getStringCellValue();    // g
//            String nvrup = row.getCell(7).getStringCellValue();       // n
//            String laboratoriesHours = row.getCell(14).getStringCellValue();  // o
//            String practiseHours = row.getCell(15).getStringCellValue();      // p
//            String typeOfPaOrGia = row.getCell(16).getStringCellValue();      // q
//            String courseWorks = row.getCell(17).getStringCellValue();        // r
//            String courseProjects = row.getCell(18).getStringCellValue();     // s
//            String courseUchAveZeOnRup = row.getCell(19).getStringCellValue(); // t
//            String prZeOnRup = row.getCell(20).getStringCellValue();          // u
//            String nirZeByRup = row.getCell(21).getStringCellValue();        // h
//            String nameof = row.getCell(9).getStringCellValue();       // j
//            String dopinfo = row.getCell(8).getStringCellValue();      // i
//
//            // ReadKW
//            String semesterOrModule = row.getCell(10).getStringCellValue();   // k
//            String weeksPerSemesterModule = row.getCell(11).getStringCellValue(); // l
//            String typeOfEducationalWork = row.getCell(12).getStringCellValue();  // m
//            String lectureHours = row.getCell(13).getStringCellValue();         // v
//
//            // InformationAboutPps
//            String department = row.getCell(32).getStringCellValue();         // ag
//            String post = row.getCell(33).getStringCellValue();               // ah
//            String termsOfAttraction = row.getCell(34).getStringCellValue();  // ai
//            String fullName = row.getCell(35).getStringCellValue();           // aj
//            String aSpecialFeature = row.getCell(36).getStringCellValue();    // ak
//
//            // TheAmountOfTeachingWorkOfTheTeachingStaff
//            String lectures = row.getCell(37).getStringCellValue();           // al
//            String practiceOrSeminars = row.getCell(38).getStringCellValue(); // am
//            String labWorksOrClinicalClasses = row.getCell(39).getStringCellValue(); // an
//            String currentControl = row.getCell(40).getStringCellValue();     // ao
//            String interimCertificationPoForBrs = row.getCell(41).getStringCellValue(); // ap
//            String registrationOfPaResults = row.getCell(42).getStringCellValue(); // aq
//            String ongoingConsultationsOnTheDiscipline = row.getCell(43).getStringCellValue(); // ar
//            String courseWorksAmount = row.getCell(44).getStringCellValue();  // as
//            String courseProjectsAmount = row.getCell(45).getStringCellValue(); // at
//            String educationalPractice = row.getCell(46).getStringCellValue(); // au
//            String procPedagogicalAndPreGraduatePractices = row.getCell(47).getStringCellValue(); // av
//            String nir = row.getCell(48).getStringCellValue();                // aw
//            String practicesIncludingResearchOfDigitalMagistracies = row.getCell(49).getStringCellValue(); // ax
//            String reviewingTheAbstractsOfGraduateStudents = row.getCell(50).getStringCellValue(); // ay
//            String candidatesExams = row.getCell(51).getStringCellValue();    // az
//            String scientificGuidance = row.getCell(52).getStringCellValue(); // ba
//            String theLeadershipOfTheWrcOrTheNkr = row.getCell(53).getStringCellValue(); // bb
//            String reviewOfTheWrc = row.getCell(54).getStringCellValue();     // bc
//            String gek = row.getCell(55).getStringCellValue();                // bd
//            String total = row.getCell(56).getStringCellValue();              // be
//
//            // Semesters
//            String auditionWork = row.getCell(58).getStringCellValue();       // bg
//            String pairsPerWeek = row.getCell(59).getStringCellValue();       // bh
//            String activities = row.getCell(60).getStringCellValue();         // bi






    }

    private Map<String, List<String>> addOopToMapFromSheet(XSSFSheet s) {
        Map<String, List<String>> map = new HashMap<>();
        int minOOP = 2;
        int maxOOP = s.getLastRowNum();
        List<String> list;

        for (int i = minOOP; i <= maxOOP; i++) {
            XSSFRow row = s.getRow(i);
            String codeOPOPVO       = row.getCell(0).getStringCellValue();
            String codeOKSO         = row.getCell(1).getStringCellValue();
            String formOfEd         = row.getCell(2).getStringCellValue();
            String lang             = row.getCell(3).getStringCellValue();
            String studyPeriod      = row.getCell(4).getStringCellValue();
            String levelOfOp        = row.getCell(5).getStringCellValue();
            String nameOfOrintation = row.getCell(6).getStringCellValue();
            String nameOfOPOPVO     = row.getCell(7).getStringCellValue();
            String partnerOfUni     = row.getCell(8).getStringCellValue();
            String OYP              = row.getCell(9).getStringCellValue();
            list = new ArrayList<>(Arrays.asList(codeOKSO, formOfEd, lang, studyPeriod, levelOfOp, nameOfOrintation, nameOfOPOPVO, partnerOfUni, OYP));
            map.put(codeOPOPVO, list);
        }

        return map;
    }

    private Map<String, List<String>> addUrNormsToMapFromSheet(XSSFSheet s) {
        int minURNorm = 4;
        int maxURNorm = s.getLastRowNum();
        Map<String, List<String>> map = new HashMap<>();
        List<String> list;
        for (int i = minURNorm; i < maxURNorm; i++){
            XSSFRow row = s.getRow(i);

            String number             = dataFormatter.formatCellValue(row.getCell(0));
            String typeOfWorks        = row.getCell(1).getStringCellValue();
            String unitOfWorkVolume   = row.getCell(2).getStringCellValue();
            String timeNorm           = dataFormatter.formatCellValue(row.getCell(3));
            String value              = dataFormatter.formatCellValue(row.getCell(4));
            String CodeTypesOfEdWorks = row.getCell(5).getStringCellValue();
            String name               = row.getCell(6).getStringCellValue();
            String notes              = row.getCell(7).getStringCellValue();

            list = new ArrayList<>(Arrays.asList(typeOfWorks, unitOfWorkVolume, timeNorm, value, CodeTypesOfEdWorks, name, notes));
            map.put(number, list);
        }
        return map;
    }

    private Map<String, String> addDepartmentsToMapFromSheet(XSSFSheet s) {
        Map<String, String> map = new HashMap<>();
        int minDepList = 1;
        int maxDepList = s.getLastRowNum();

        for (int i = minDepList; i < maxDepList; i++){
            XSSFRow row = s.getRow(i);
            String faculty = row.getCell(0).getStringCellValue();
            String department = row.getCell(1).getStringCellValue();
            map.put(department, faculty);
        }

        return map;
    }

    private Map<String, String> addInitialsToMapFromSheet(XSSFSheet s) {
        Map<String, String> map = new HashMap<>();

        List<Integer> list = new ArrayList<>(Arrays.asList( // ID = excelIndex - 1
                 1,  3,  6, 10, // Форма обучения и уровень ОП
                13, 18, 21, 25,
                28, 30, 33, 40,
                43, 72, 75, 78,
                81, 83
        ));

        int len = list.size() >> 1;
        for (int i = 0; i < len; i++) {
            int min = list.get(2 * i), max = list.get((2 * i) + 1);

            for (int j = min; j <= max; j++){
                XSSFRow row = s.getRow(j);
                String s1 = row.getCell(0).getStringCellValue();
                String s2 = row.getCell(1).getStringCellValue();
//                System.out.println(j + " " + s1 + " " + s2);
                map.put(s2, s1);
            }
        }
        return map;
    }
}
