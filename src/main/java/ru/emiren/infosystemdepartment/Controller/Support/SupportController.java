package ru.emiren.infosystemdepartment.Controller.Support;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Model.Support.Data;
import ru.emiren.infosystemdepartment.Repository.Support.DataRepository;
import ru.emiren.infosystemdepartment.Service.Email.EmailService;
import ru.emiren.infosystemdepartment.Service.Support.DataService;

@Controller
@RequestMapping("/support")
public class SupportController {
    private final DataService dataService;
    private final DataRepository dataRepository;
    private final EmailService emailService;

    @Autowired
    public SupportController(DataService dataService, DataRepository dataRepository, EmailService emailService) {
        this.dataService = dataService;
        this.dataRepository = dataRepository;
        this.emailService = emailService;
    }

    @GetMapping("")
    public String main(Model model) {
        Data data = new Data();
        model.addAttribute("data", data);
        model.addAttribute("archiveFlag", false);
        return "support";
    }

    @Async
    @PostMapping("")
    public String saveData(@Valid @ModelAttribute("data") DataDTO data, Model model) {
        emailService.sendSimpleMail("mrjava3300@mail.ru", "Ticket"+data.id, data.getDescription());
        dataService.saveData(data);
        return "redirect:/support";
    }

    @GetMapping("/api/view_ticket/{id}")
    public String viewTicket(@PathVariable int id, Model model) {
        DataDTO data = dataService.getDataById(id);
        model.addAttribute("id", data.id);
        model.addAttribute("message", data.description);
        model.addAttribute("email", data.email);
        model.addAttribute("phone", data.phone);
        return "ticket";
    }
}
