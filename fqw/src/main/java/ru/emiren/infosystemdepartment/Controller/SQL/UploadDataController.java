package ru.emiren.infosystemdepartment.Controller.SQL;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.Model.SQL.*;
import ru.emiren.infosystemdepartment.Service.api.UploadDataFormService;
import ru.emiren.infosystemdepartment.Util.DateUtil;

import java.util.Map;

@RestController
@RequestMapping("")
@Slf4j
public class UploadDataController {


    private UploadDataFormService uploadDataFormService;

    @Autowired
    public UploadDataController(UploadDataFormService uploadDataFormService) {
        this.uploadDataFormService = uploadDataFormService;
    }

    /**
     * Handle for upload data form.
     * I recommend to not touch.
     * @param request
     * @return a redirection to the same page
     */
    @PostMapping("/api/add-data")
    public ResponseEntity<String> addData(@RequestBody Map<String, String> request){
        log.info(request.toString());
        Integer dateOfProtection = DateUtil.parseYear(request.get("dateOfProtection"));

        log.info("Saving Department");
        Department department = uploadDataFormService.createDepartment(request);
        log.info("Saving Orientation");
        Orientation orientation = uploadDataFormService.createOrientation(request);
        log.info("Saving Protection");
        Protection protection = uploadDataFormService.createProtection(orientation, dateOfProtection);
        log.info("Saving Reviewer");
        Reviewer reviewer = uploadDataFormService.createReviewer(request);

        log.info("Saving Decree");
        Decree decree = uploadDataFormService.createDecree(request);
        log.info("Saving FQW");
        FQW fqw = uploadDataFormService.createFQW(request, reviewer, decree);

        log.info("Saving Commissioner 1");
        Commissioner commissioner1 = uploadDataFormService.createCommissioner(request, "1");
        log.info("Saving Commissioner 2");
        Commissioner commissioner2 = uploadDataFormService.createCommissioner(request, "2");
        log.info("Saving Commissioner 3");
        Commissioner commissioner3 = uploadDataFormService.createCommissioner(request, "3");

        log.info("Saving Student");
        Student student = uploadDataFormService.createStudent(request, orientation, fqw, department);
        log.info("Saving Lecturer");
        Lecturer lecturer = uploadDataFormService.createLecturer(request, department);
        log.info("Saving Student-Lecturer");
        StudentLecturers sl = uploadDataFormService.createStudentLecturers(request, student, lecturer);

        log.info("Saving Protocol");
        Protocol protocol = uploadDataFormService.createProtocol(request, student);

        log.info("Saving Question 1");
        Question q1 = uploadDataFormService.createQuestion(request, "1", commissioner1);
        log.info("Saving Question 2");
        Question q2 = uploadDataFormService.createQuestion(request, "2", commissioner2);
        log.info("Saving Question 3");
        Question q3 = uploadDataFormService.createQuestion(request, "3", commissioner3);

        log.info("Saving PC 1");
        uploadDataFormService.createProtectionCommissioner(protection, commissioner1);
        log.info("Saving PC 2");
        uploadDataFormService.createProtectionCommissioner(protection, commissioner2);
        log.info("Saving PC 3");
        uploadDataFormService.createProtectionCommissioner(protection, commissioner3);

        log.info("Saving PQ 1");
        ProtocolQuestion pq1 = uploadDataFormService.createProtocolQuestion(request, protocol, q1);
        log.info("Saving PQ 2");
        ProtocolQuestion pq2 = uploadDataFormService.createProtocolQuestion(request, protocol, q2);
        log.info("Saving PQ 3");
        ProtocolQuestion pq3 = uploadDataFormService.createProtocolQuestion(request, protocol, q3);

        lecturer.getStudents().add(sl);
        orientation.getProtection().add(protection);
        return ResponseEntity.status(HttpStatus.OK).body("Done");
    }

}
