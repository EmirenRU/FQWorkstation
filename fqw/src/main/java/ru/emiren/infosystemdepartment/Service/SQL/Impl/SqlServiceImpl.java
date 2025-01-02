package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
import ru.emiren.infosystemdepartment.Model.SQL.Year;
import ru.emiren.infosystemdepartment.Repository.SQL.YearRepository;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SqlServiceImpl implements SqlService {
    StudentService studentService;
    DepartmentService departmentService;
    LecturerService lecturerService;
    OrientationService orientationService;
    ProtectionService protectionService;
    StudentLecturersService studentLecturersService;
    FQWService fqwService;
    WordService wordService;
    YearRepository yearRepository;

    List<LecturerDTO> lecturerDTOS;
    List<OrientationDTO> orientationDTOS;
    List<DepartmentDTO> departmentDTOS;
    List<FQWDTO> fqwdtos;
    List<Year> years;

    DateTimeFormatter dateTimeFormatter;

    @DateTimeFormat(pattern = "yyyy")
    private LocalDate date;

    private final List<String> params = new ArrayList<>(
            List.of("lecturers_selector", "departments_selector",
                    "orientations_selector", "themes_selector",
                    "years_selector", "specificLecturer",
                    "studentLecturers_container"
            )
    );

    @Autowired
    public SqlServiceImpl(StudentService studentService,
                          DepartmentService departmentService,
                          LecturerService lecturerService,
                          OrientationService orientationService,
                          ProtectionService protectionService,
                          StudentLecturersService studentLecturersService,
                          FQWService fqwService,
                          YearRepository yearRepository,
                          WordService wordService
    ){
        this.studentService             = studentService;
        this.departmentService          = departmentService;
        this.lecturerService            = lecturerService;
        this.orientationService         = orientationService;
        this.protectionService          = protectionService;
        this.studentLecturersService    = studentLecturersService;
        this.fqwService                 = fqwService;
        this.wordService                = wordService;
        this.yearRepository             = yearRepository;

        lecturerDTOS       = lecturerService.getAllLecturer();
        departmentDTOS     = departmentService.getAllDepartments();
        orientationDTOS    = orientationService.getAllOrientations();
        years              = yearRepository.findAll();
        fqwdtos            = fqwService.getAllFQW();
        dateTimeFormatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }


    @Override
    public String viewLecturer(Model model) {

        model.addAttribute(params.getFirst(), lecturerDTOS);
        model.addAttribute(params.get(1), departmentDTOS);
        model.addAttribute(params.get(2), orientationDTOS);
        model.addAttribute(params.get(3), fqwdtos);
        model.addAttribute(params.get(4), years);

        return "lecturers";
    }

    @Override
    public String viewLecturerAsync(Model model) {
        model.addAttribute(params.getFirst(), lecturerDTOS);
        model.addAttribute(params.get(1), departmentDTOS);
        model.addAttribute(params.get(2), orientationDTOS);
        model.addAttribute(params.get(3), fqwdtos);
        model.addAttribute(params.get(4), years);

        return "lecturers";
    }

    @Override
    @Async
    public CompletableFuture<String> getLecturerAsync(Model model) {
        model.addAttribute(params.getFirst(), lecturerDTOS);
        model.addAttribute(params.get(1), departmentDTOS);
        model.addAttribute(params.get(2), orientationDTOS);
        model.addAttribute(params.get(3), fqwdtos);
        model.addAttribute(params.get(4), years);

        log.info("View Lecturer model prepared asynchronously.");
        return CompletableFuture.completedFuture("lecturers");
    }

    @Override
    public CompletableFuture<String> getLecturersAsync(HttpServletRequest request, Model model) {
        String[] lecturerParams = request.getParameterValues("lecturer"); // Long
        List<Long> lecturerIds = (lecturerParams != null) ? Arrays.stream(lecturerParams).map(Long::parseLong).toList() : List.of((long) -1);
        String[] orientationParams = request.getParameterValues("orientation");
        List<String> orientationCodes = (orientationParams != null) ? Arrays.asList(orientationParams) : List.of("-1");
        String[] departmentParams = request.getParameterValues("department"); // Long
        List<Long> departmentCode = (departmentParams != null) ? Arrays.stream(departmentParams).map(Long::parseLong).toList() : List.of((long) -1);
        String[] themeParams = request.getParameterValues("themes");
        List<String> theme = (themeParams != null) ? Arrays.asList(themeParams) : List.of("-1");
        String strDateFrom =request.getParameter("from");
        String strDateTo =request.getParameter("to");

        log.info("lecturer: {} orientation: {} department: {} theme: {} DateFrom: {} DateTo: {}",
                lecturerIds,
                orientationParams,
                departmentCode,
                theme,
                strDateFrom,
                strDateTo);

        java.time.Year dateFrom = null;
        java.time.Year dateTo = null;

        if (!strDateFrom.isEmpty() && !strDateTo.isEmpty()){
            dateFrom = java.time.Year.parse(strDateFrom);
            dateTo   = java.time.Year.parse(strDateTo);
        }

        log.info("date from and to: {} and {}", dateFrom, dateTo );

        String stringForQuery = String.join("||",
                theme.stream()
                        .map(word -> "%" + word + "%").collect(Collectors.toList())
        ); // TODO convert list to sql format string for comparisons

        List<StudentLecturersDTO> res = studentLecturersService.findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(
                orientationCodes,
                departmentCode,
                dateFrom,
                dateTo,
                stringForQuery,
                lecturerIds
        );

        model.addAttribute(params.get(6), res);
        log.info("The result is {} empty", res.isEmpty());

        model.addAttribute(params.getFirst(), lecturerDTOS);
        model.addAttribute(params.get(1), departmentDTOS);
        model.addAttribute(params.get(2), orientationDTOS);
        model.addAttribute(params.get(3), fqwdtos);
        model.addAttribute(params.get(4), years);

        model.addAttribute("flag", true);

        if (lecturerIds.getFirst() == -1){
            model.addAttribute(params.get(5), lecturerService.createDummyLecturer());
            log.info("Async getLecturer task has completed");
            return CompletableFuture.completedFuture("lecturers");
        }

        // TODO make one or ALL
        // todo or suggestion: EXCEL API to SAVE Object to Repository
        // TODO api for android client

        List<LecturerDTO> lecturerDTO = lecturerService.findDtoByLecturerId(lecturerIds);

        model.addAttribute(params.get(5), lecturerDTO);

        log.info("Async getLecturer task has completed");
        return CompletableFuture.completedFuture("lecturers");
    }


    @Override
    public String createLectureForm(Model model, String year, HttpServletRequest request) {
        List<StudentLecturersDTO> list =  studentLecturersService.findAllAndSortedByDate(year);

        if (list.isEmpty()){
            return "redirect:/sql/lecturers";
        }

        request.setAttribute("flag", true);
        request.setAttribute(params.get(5), lecturerService.createDummyLecturer());
        request.setAttribute(params.get(6), list);
        return "forward:/sql/lecturers/view";
    }

    @Override
    public String getLecturers(HttpServletRequest request, Model model) {
        String[] lecturerParams = request.getParameterValues("lecturer"); // Long
        List<Long> lecturerIds = (lecturerParams != null) ? Arrays.stream(lecturerParams).map(Long::parseLong).toList() : List.of((long) -1);
        String[] orientationParams = request.getParameterValues("orientation");
        List<String> orientationCodes = (orientationParams != null) ? Arrays.asList(orientationParams) : List.of("-1");
        String[] departmentParams = request.getParameterValues("department"); // Long
        List<Long> departmentCode = (departmentParams != null) ? Arrays.stream(departmentParams).map(Long::parseLong).toList() : List.of((long) -1);
        String[] themeParams = request.getParameterValues("themes");
        List<String> theme = (themeParams != null) ? Arrays.asList(themeParams) : List.of("-1");
        String strDateFrom =request.getParameter("from");
        String strDateTo =request.getParameter("to");

        log.info("lecturer: {} orientation: {} department: {} theme: {} DateFrom: {} DateTo: {}",
                lecturerIds,
                orientationParams,
                departmentCode,
                theme,
                strDateFrom,
                strDateTo);

        java.time.Year dateFrom = null;
        java.time.Year dateTo = null;

        if (!strDateFrom.isEmpty() && !strDateTo.isEmpty()){
            dateFrom = java.time.Year.parse(strDateFrom);
            dateTo   = java.time.Year.parse(strDateTo);
        }

        log.info("date from and to: {} and {}", dateFrom, dateTo );

        String stringForQuery = String.join("||",
                theme.stream()
                        .map(word -> "%" + word + "%").collect(Collectors.toList())
        ); // TODO convert list to sql format string for comparisons

        List<StudentLecturersDTO> res = studentLecturersService.findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(
                orientationCodes,
                departmentCode,
                dateFrom,
                dateTo,
                stringForQuery,
                lecturerIds
        );

        model.addAttribute(params.get(6), res);
        log.info("The result is {} empty", res.isEmpty());

        model.addAttribute(params.getFirst(), lecturerDTOS);
        model.addAttribute(params.get(1), departmentDTOS);
        model.addAttribute(params.get(2), orientationDTOS);
        model.addAttribute(params.get(3), fqwdtos);
        model.addAttribute(params.get(4), years);

        model.addAttribute("flag", true);

        if (lecturerIds.getFirst() == -1){
            model.addAttribute(params.get(5), lecturerService.createDummyLecturer());
            return "lecturers";
        }

        // TODO make one or ALL
        // todo or suggestion: EXCEL API to SAVE Object to Repository
        // TODO api for android client

        List<LecturerDTO> lecturerDTO = lecturerService.findDtoByLecturerId(lecturerIds);


        model.addAttribute(params.get(5), lecturerDTO);

        return "lecturers";
    }
}
