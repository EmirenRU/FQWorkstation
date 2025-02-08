package ru.emiren.support.service.impl;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import ru.emiren.support.dto.EmailPayload;
import ru.emiren.support.dto.SupportDataDTO;
import ru.emiren.support.mapper.SupportDataMapper;
import ru.emiren.support.model.SupportData;
import ru.emiren.support.repository.SupportDataRepository;
import ru.emiren.support.service.SupportService;

import java.util.List;
import java.util.Map;

import static ru.emiren.support.mapper.SupportDataMapper.dataDTOToData;

@Service
@Slf4j
public class SupportServiceImpl implements SupportService {
    private RestTemplate restTemplate;
    private SupportDataRepository supportDataRepository;
    private final Gson gson;

    private List<SupportDataDTO> dataDTOList;

    private static final String location = "http://localhost:13132";

    @Autowired
    public SupportServiceImpl(RestTemplate restTemplate, SupportDataRepository supportDataRepository, Gson gson) {
        this.restTemplate = restTemplate;
        this.supportDataRepository = supportDataRepository;
        this.gson = gson;
        dataDTOList = supportDataRepository.findAll().stream().map(SupportDataMapper::dataToDataDTO).toList();
    }

    public void setRestTemplate(RestTemplate restTemplate, SupportDataRepository supportDataRepository) {
        this.restTemplate = restTemplate;
        this.supportDataRepository = supportDataRepository;
    }

    @Override
    public SupportData saveData(SupportDataDTO dataDTO) {
        return supportDataRepository.save(dataDTOToData(dataDTO));
    }

    @Override
    public SupportDataDTO getDataById(int id) {
        return supportDataRepository.findById((long) id)
                .stream()
                .map(SupportDataMapper::dataToDataDTO)
                .toList()
                .getFirst();
    }

    @Override
    public String getDataById(int id, Model model) {
        SupportDataDTO data = getDataById(id);
        model.addAttribute("id", data.getId());
        model.addAttribute("message", data.getDescription());
        model.addAttribute("email", data.getEmail());
        model.addAttribute("phone", data.getPhone());
        return "ticket";
    }

    @Override
    public ResponseEntity<?> sendSimpleEmailAndSaveData(String to, SupportDataDTO data, Model model) {
        saveData(data);
        return sendEmail(to, "Ticket"+data.getId(), data.getDescription());
    }

    @Override
    public String returnMainPage(Model model) {
        model.addAttribute("data", new SupportData());
        return "support";
    }

    @Override
    public ResponseEntity<?> sendEmail(String to, String subject, String body) {
        EmailPayload payload = new EmailPayload(to, subject, body);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmailPayload> request = new HttpEntity<>(payload, headers);

        return restTemplate.exchange(location, HttpMethod.POST, request, String.class);
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

        SupportData data = new SupportData();
        data.setFullName(username);
        data.setEmail(email);
        data.setPhone(phone);
        data.setDescription(message);
        if (!data.getDescription().isEmpty()) {
            supportDataRepository.save(data);
        }
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @Override
    public ResponseEntity<String> getAllTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dataDTOList));

    }

    @Override
    public ResponseEntity<String> viewTicket(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("ID"));
        SupportDataDTO res = supportDataRepository.findById(id).map(SupportDataMapper::dataToDataDTO).orElse(null);
        if (res != null) {
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(res));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void updateListDTO(){
        log.info("Updating list of Support Data DTO");
//        if (!dataDTOList.isEmpty()) {
//            log.info("Cleaning List beforehand");
//            dataDTOList.clear();
//        }
        log.info("Updating list of Data DTO");
        dataDTOList = supportDataRepository.findAll().stream().map(SupportDataMapper::dataToDataDTO).toList();
    }

}
