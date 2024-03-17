package ru.emiren.infosystemdepartment.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.DTO.DepartmentDTO;
import ru.emiren.infosystemdepartment.DTO.LecturerDTO;
import ru.emiren.infosystemdepartment.DTO.OrientationDTO;
import ru.emiren.infosystemdepartment.DTO.StudentDTO;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.*;

import java.util.List;

@Controller
@RequestMapping("/sql")
public class SqlController {

    StudentService studentService;
    DepartmentService departmentService;
    LecturerService lecturerService;
    OrientationService orientationService;
    ProtectionService protectionService;

    @Autowired
    public SqlController(StudentService studentService, DepartmentService departmentService, LecturerService lecturerService, OrientationService orientationService, ProtectionService protectionService){
        this.studentService = studentService;
        this.departmentService = departmentService;
        this.lecturerService = lecturerService;
        this.orientationService = orientationService;
        this.protectionService = protectionService;
    }

    @GetMapping("")
    public String main(Model model){
        return "redirect:/lecturer";
    }

    @GetMapping("lecturers")
    public String CreateLectureForm(Model model){
        model.addAttribute("lecturers", lecturerService.getAllLecturer());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("orientations", orientationService.getAllOrientations());
        return "lecturers";
    }

    @PostMapping("lecturers")
    public String getLecturerers(@RequestParam("lecturer") Long lecturerId,
                                 @RequestParam("department") String departmentCode,
                                 @RequestParam("orientation") String orientationCode,
                                 Model model){

        LecturerDTO lecturerDTO = lecturerService.findByLecturerId(lecturerId);
        if (lecturerDTO == null){
            model.addAttribute("lecturers", lecturerService.getAllLecturer());
            return "redirect:/lecturers";
        }

        model.addAttribute("students", studentService.findAllStudentByLecturerId(lecturerId));
        model.addAttribute("lecturers", lecturerService.getAllLecturer());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("orientations", orientationService.getAllOrientations());

        return "lecturers";
    }

    @GetMapping("Students")
    public String CreateStudentForm(Model model){

        return "students";
    }
}
