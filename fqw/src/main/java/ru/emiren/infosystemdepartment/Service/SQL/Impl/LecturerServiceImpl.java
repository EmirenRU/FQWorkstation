package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.LecturerDTO;
import ru.emiren.infosystemdepartment.Mapper.LecturerMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;
import ru.emiren.infosystemdepartment.Repository.SQL.LecturerRepository;
import ru.emiren.infosystemdepartment.Service.SQL.LecturerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LecturerServiceImpl implements LecturerService {

    private LecturerRepository lecturerRepository;

    @Autowired
    public LecturerServiceImpl(LecturerRepository lecturerRepository){
        this.lecturerRepository = lecturerRepository;
    }


    @Override
    public List<LecturerDTO> findDtoByLecturerId(List<Long> id) {
        return lecturerRepository.findById(id)
                .stream()
                .map(LecturerMapper::mapToLecturerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Lecturer findByLecturerId(Long id) {
        return lecturerRepository.findById(id).orElse(null);
    }

    @Override
    public Lecturer findByLecturerName(String name) {
        return lecturerRepository.findLecturerByName(name).orElse(null);
    }

    @Override
    public List<LecturerDTO> getAllLecturer() {
        return lecturerRepository.findAll()
                .stream()
                .map(LecturerMapper::mapToLecturerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LecturerDTO createDummyLecturer() {
        return new LecturerDTO().builder().id(null).build();
    }

    @Override
    public List<LecturerDTO> findAllLecturersById(Long lecturerId) {
        return lecturerRepository.findAll()
                .stream()
                .map(LecturerMapper::mapToLecturerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Lecturer saveLecturer(Lecturer lecturerDTO) {
        return lecturerRepository.save(lecturerDTO);
    }

    @Override
    public void deleteLecturer(Lecturer lecturer) {
        lecturerRepository.delete(lecturer);
    }

    @Override
    public Lecturer updateLecturer(Long id, Lecturer lecturer) {
        Lecturer oldLecturer = lecturerRepository.findById(id).orElse(new Lecturer());

        if (lecturer.getName() != null) { oldLecturer.setName(lecturer.getName()); }
        if (lecturer.getAcademicDegree() != null) { oldLecturer.setAcademicDegree(lecturer.getAcademicDegree());}
        if (lecturer.getPosition() != null) { oldLecturer.setPosition(lecturer.getPosition());}
        if (lecturer.getDepartment() != null) { oldLecturer.setDepartment(lecturer.getDepartment());}
        if (lecturer.getStudents() != null) { oldLecturer.setStudents(lecturer.getStudents());}

        return lecturerRepository.save(oldLecturer);
    }

    @Override
    public List<LecturerDTO> getConnectedLecturers() {
        return lecturerRepository.getAllConnectedLecturers().stream().map(LecturerMapper::mapToLecturerDTO).toList();
    }

    @Override
    public Long getMaxId() {
        return lecturerRepository.getMaxId();
    }
}
