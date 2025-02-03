package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.Payload.SqlPayload;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.LecturerMapper;
import ru.emiren.infosystemdepartment.Mapper.SQL.StudentLecturersMapper;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;
import ru.emiren.infosystemdepartment.Repository.SQL.StudentLecturerRepository;
import ru.emiren.infosystemdepartment.Service.SQL.StudentLecturersService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentLecturersServiceImpl implements StudentLecturersService {
    StudentLecturerRepository studentLecturerRepository;

    StudentLecturersServiceImpl(StudentLecturerRepository studentLecturerRepository){
        this.studentLecturerRepository = studentLecturerRepository;
    }


    @Override
    public StudentLecturers saveStudentLecturers(StudentLecturers studentLecturersDTO) {
        return studentLecturerRepository.save(studentLecturersDTO);
    }

    @Override
    public List<SqlPayload> getAllStudentLecturers() {
        return studentLecturerRepository.findAll()
                .stream()
                .map(StudentLecturersMapper::mapToSqlPayload)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentLecturersDTO> findAllAndSortedByLecturerName() {
        return studentLecturerRepository.findAllSorted().stream()
                .map(StudentLecturersMapper::mapToStudentLecturersDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentLecturersDTO> findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartment
            (List<String> orientationCodes,
             List<Long> departmentCode,
             Integer dateFrom,
             Integer dateTo,
             List<Long> theme,
             List<Long> lecturerId
            ) {
        log.info("lecturer: {} orientation: {} department: {} theme: {} DateFrom: {} DateTo: {}",
                lecturerId,
                orientationCodes,
                departmentCode,
                theme,
                dateFrom,
                dateTo);
        return studentLecturerRepository.findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment
        (orientationCodes,
        departmentCode,
        dateFrom,
        dateTo,
        theme,
        lecturerId).stream()
                .map(StudentLecturersMapper::mapToStudentLecturersDTO)
                .toList();
    }

    @Override
    public List<StudentLecturersDTO> findAllAndSortedByDate(String date) {
        return studentLecturerRepository.findAllSortedByDate(date).stream()
                .map(StudentLecturersMapper::mapToStudentLecturersDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStudentLecturers(StudentLecturers sl) {
        studentLecturerRepository.delete(sl);
    }

    @Override
    public StudentLecturers findStudentLecturerById(Long id) {
        return studentLecturerRepository.findById(id).orElse(null);
    }

    @Override
    public StudentLecturersDTO findStudentLecturersDTOById(Long id) {
        return studentLecturerRepository.findById(id)
                .map(StudentLecturersMapper::mapToStudentLecturersDTO)
                .orElse(null);
    }

    @Override
    public StudentLecturers updateStudentLecturers(StudentLecturers sl) {
        StudentLecturers studentLecturers = findStudentLecturerById(sl.getId());

        if (studentLecturers == null) { return studentLecturerRepository.save(sl); }

        if (sl.getStudent() != null) { studentLecturers.setStudent(sl.getStudent()); }
        if (sl.getLecturer() != null) {studentLecturers.setLecturer(sl.getLecturer()); }
        if (sl.getIsConsultant() != null) studentLecturers.setIsConsultant(sl.getIsConsultant());
        if (sl.getIsScientificSupervisor() != null) studentLecturers.setIsScientificSupervisor(sl.getIsScientificSupervisor());

        return studentLecturerRepository.save(studentLecturers);
    }

    @Override
    public StudentLecturers findStudentLecturersByStudentStudNum(Long studNum) {
        return studentLecturerRepository.findByStudentNumber(studNum).orElse(null);
    }


} // C D
