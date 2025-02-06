package ru.emiren.infosystemdepartment.Service.Support.Impl;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Mapper.Support.DataMapper;
import ru.emiren.infosystemdepartment.Model.Support.Data;
import ru.emiren.infosystemdepartment.Repository.Support.DataRepository;
import ru.emiren.infosystemdepartment.Service.Email.EmailService;
import ru.emiren.infosystemdepartment.Service.Support.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.emiren.infosystemdepartment.Mapper.Support.DataMapper.dataDTOToData;

@Slf4j
@Service
public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;
    private final EmailService     emailService;
    private final Gson gson;

    private List<DataDTO> dataDTOList;

    public DataServiceImpl(DataRepository dataRepository, EmailService emailService, Gson gson) {
        this.dataRepository = dataRepository;
        this.emailService = emailService;
        this.gson = gson;
        dataDTOList = dataRepository.findAll().stream().map(DataMapper::dataToDataDTO).toList();
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

    @Override
    public ResponseEntity<String> handleMessage(Map<String, String> request) {
        log.info("Handling request: " + request.toString());
        for (Map.Entry<String, String> es : request.entrySet()) {
            log.info("Key and Value: {} and {}",es.getKey(), es.getValue());
        }
        String message = request.get("message");
        String username = request.get("username");
        String email = request.get("email");
        String phone = request.get("phone");

        Data data = new Data();
        data.setFullName(username);
        data.setEmail(email);
        data.setPhone(phone);
        data.setDescription(message);
        if (!data.getDescription().isEmpty()) {
            dataRepository.save(data);
        }
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @Override
    public ResponseEntity<String> viewTicket(HttpServletRequest request){
        Long id = Long.valueOf(request.getParameter("ID"));
        DataDTO res = dataRepository.findById(id).map(DataMapper::dataToDataDTO).orElse(null);
        if (res != null) {
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(res));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<String> getAllTickets(){
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dataDTOList));
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void updateListDTO(){
        log.info("Updating list of Support Data DTO");
//        if (!dataDTOList.isEmpty()) {
//            log.info("Cleaning List beforehand");
//            dataDTOList.clear();
//        }
        log.info("Updating list of Data DTO");
        dataDTOList = dataRepository.findAll().stream().map(DataMapper::dataToDataDTO).toList();
    }
}