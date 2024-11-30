package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.LecturerDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.LecturerMapper;
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
    public LecturerDTO findByLecturerId(Long id) {
        return LecturerMapper.mapToLecturerDTO(lecturerRepository.findById(id).get());
    }

    @Override
    public LecturerDTO findByLecturerName(String name) {
        return null;
    }

    @Override
    public List<LecturerDTO> getAllLecturer() {
        List<LecturerDTO> lecturers = lecturerRepository.findAll()
                .stream().map(LecturerMapper::mapToLecturerDTO).collect(Collectors.toList());
        return lecturers;
    }

    @Override
    public LecturerDTO createDummyLecturer() {
        return new LecturerDTO().builder()
                .id(null)
                .build();
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
}
