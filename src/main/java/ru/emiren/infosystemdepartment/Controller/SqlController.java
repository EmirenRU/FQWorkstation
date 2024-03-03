package ru.emiren.infosystemdepartment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.emiren.infosystemdepartment.DTO.LecturerDTO;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.DepartmentService;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.LecturerService;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.OrientationService;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.StudentService;

import java.util.List;

@Controller
@RequestMapping("/sql")
public class SqlController {

    StudentService studentService;
    DepartmentService departmentService;
    LecturerService lecturerService;
    OrientationService orientationService;

    @Autowired
    public SqlController(StudentService studentService, DepartmentService departmentService, LecturerService lecturerService, OrientationService orientationService){
        this.studentService = studentService;
        this.departmentService = departmentService;
        this.lecturerService = lecturerService;
        this.orientationService = orientationService;
    }

    @GetMapping("")
    public String main(Model model){
        return "";
    }

    @GetMapping("lecturer")
    public String CreateLectureForm(Model model){
        List<LecturerDTO> lecturers = lecturerService.getAllLecturer();

        model.addAttribute("lecturer", lecturers);
        return "lecturers";
    }

    @GetMapping("Students")
    public String CreateStudentForm(Model model){


        return "students";
    }
}
