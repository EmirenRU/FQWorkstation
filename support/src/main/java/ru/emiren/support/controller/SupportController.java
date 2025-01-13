package ru.emiren.support.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.emiren.support.dto.SupportDataDTO;
import ru.emiren.support.service.SupportService;

@Controller
public class SupportController {
    private SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @GetMapping("")
    public String main(Model model) {
        return supportService.returnMainPage(model);
    }

    @PostMapping("")
    public ResponseEntity<?> saveData(String to, @Valid @ModelAttribute("data") SupportDataDTO data, Model model) {
        return supportService.sendSimpleEmailAndSaveData(to, data, model);
    }

    @GetMapping("/api/view_ticket/{id}")
    public String viewTicket(@PathVariable int id, Model model) {
        return supportService.getDataById(id, model);
    }
}
