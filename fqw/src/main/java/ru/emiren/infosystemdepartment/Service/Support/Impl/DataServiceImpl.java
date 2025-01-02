package ru.emiren.infosystemdepartment.Service.Support.Impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Mapper.Support.DataMapper;
import ru.emiren.infosystemdepartment.Model.Support.Data;
import ru.emiren.infosystemdepartment.Repository.Support.DataRepository;
import ru.emiren.infosystemdepartment.Service.Email.EmailService;
import ru.emiren.infosystemdepartment.Service.Support.DataService;

import java.util.stream.Collectors;

import static ru.emiren.infosystemdepartment.Mapper.Support.DataMapper.dataDTOToData;

@Service
public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;
    private final EmailService     emailService;

    public DataServiceImpl(DataRepository dataRepository, EmailService emailService) {
        this.dataRepository = dataRepository;
        this.emailService = emailService;
    }

    @Override
    public Data saveData(DataDTO dataDTO) {
        return dataRepository.save(dataDTOToData(dataDTO));
    }

    @Override
    public DataDTO getDataById(int id) {
        return dataRepository.findById((long) id)
                .stream()
                .map(DataMapper::dataToDataDTO)
                .toList()
                .getFirst();
    }

    @Override
    public String getDataById(int id, Model model) {
        DataDTO data = getDataById(id);
        model.addAttribute("id", data.getId());
        model.addAttribute("message", data.getDescription());
        model.addAttribute("email", data.getEmail());
        model.addAttribute("phone", data.getPhone());
        return "ticket";
    }

    @Override
    public String sendSimpleEmailAndSaveData(DataDTO data, Model model) {
        emailService.sendSimpleMail("mrjava3300@mail.ru", "Ticket"+data.getId(), data.getDescription());
        saveData(data);
        return "redirect:/support";
    }

    @Override
    public String returnMainPage(Model model) {
        model.addAttribute("data", new Data());
        return "support";
    }
}
