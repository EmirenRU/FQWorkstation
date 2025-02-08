package ru.emiren.support.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import ru.emiren.support.dto.SupportDataDTO;
import ru.emiren.support.model.SupportData;

import java.util.Map;

public interface SupportService {
    SupportData saveData(SupportDataDTO data);

    SupportDataDTO getDataById(int id);

    String getDataById(int id, Model model);

    ResponseEntity<?> sendSimpleEmailAndSaveData(String to, @Valid SupportDataDTO data, Model model);

    String returnMainPage(Model model);

    ResponseEntity<?> sendEmail(String to, String subject, String body);

    ResponseEntity<String> handleMessage(Map<String, String> request);
    ResponseEntity<String> getAllTickets();
    ResponseEntity<String> viewTicket(HttpServletRequest request);
}
