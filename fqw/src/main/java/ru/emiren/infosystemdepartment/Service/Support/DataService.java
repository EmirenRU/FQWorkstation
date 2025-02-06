package ru.emiren.infosystemdepartment.Service.Support;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Model.Support.Data;

import java.util.Map;

public interface DataService {
    Data saveData(DataDTO data);

    DataDTO getDataById(int id);

    String getDataById(int id, Model model);

    String sendSimpleEmailAndSaveData(@Valid DataDTO data, Model model);

    String returnMainPage(Model model);

    ResponseEntity<String> handleMessage(Map<String, String> request);
    ResponseEntity<String> getAllTickets();
    ResponseEntity<String> viewTicket(HttpServletRequest request);
}
