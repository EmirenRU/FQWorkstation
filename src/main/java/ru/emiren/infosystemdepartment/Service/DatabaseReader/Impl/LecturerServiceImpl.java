package ru.emiren.infosystemdepartment.Service.DatabaseReader.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.LecturerDTO;
import ru.emiren.infosystemdepartment.Mapper.LecturerMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;
import ru.emiren.infosystemdepartment.Repository.LecturerRepository;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.LecturerService;

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
        List<Lecturer> lecturers = lecturerRepository.findAll();
        return lecturers.stream().map(LecturerMapper::mapToLecturerDTO).collect(Collectors.toList());
    }

    @Override
    public List<LecturerDTO> findAllLecturersById(Long lecturerId) {
        return lecturerRepository.findAll()
                .stream()
                .map(LecturerMapper::mapToLecturerDTO)
                .collect(Collectors.toList());
    }
}
