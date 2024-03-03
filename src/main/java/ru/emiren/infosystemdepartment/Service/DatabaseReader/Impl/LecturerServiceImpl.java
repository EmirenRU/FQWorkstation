package ru.emiren.infosystemdepartment.Service.DatabaseReader.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.LecturerDTO;
import ru.emiren.infosystemdepartment.Mapper.LecturerMapper;
import ru.emiren.infosystemdepartment.Model.Lecturer;
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
    public List<LecturerDTO> getAllLecturer() {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        return lecturers.stream().map(LecturerMapper::mapToLecturerDTO).collect(Collectors.toList());
    }
}
