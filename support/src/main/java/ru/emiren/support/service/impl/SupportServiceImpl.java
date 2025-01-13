package ru.emiren.support.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import ru.emiren.support.dto.EmailPayload;
import ru.emiren.support.dto.SupportDataDTO;
import ru.emiren.support.mapper.SupportDataMapper;
import ru.emiren.support.model.SupportData;
import ru.emiren.support.repository.SupportDataRepository;
import ru.emiren.support.service.SupportService;

import static ru.emiren.support.mapper.SupportDataMapper.dataDTOToData;

@Service
public class SupportServiceImpl implements SupportService {
    private RestTemplate restTemplate;
    private SupportDataRepository supportDataRepository;

    @Value("${email.location.uri}")
    private String location;

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

}
