package ru.emiren.infosystemdepartment.Controller.Support;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Service.Support.DataService;

@Controller
@RequestMapping("/support")
public class SupportController {
    private final DataService dataService;

    @Autowired
    public SupportController(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * Provide a support page
     * @param model
     * @return a page with DTO to gather all info
     */
    @GetMapping("")
    public String main(Model model) {
        return dataService.returnMainPage(model);
    }

    /**
     * Handle data from client to do something and send simple email
     * @param data data from client
     * @param model do nothing. it can be removed, probably?
     * @return a redirect to support page
     */
    @PostMapping("")
    public String saveData(@Valid @ModelAttribute("data") DataDTO data, Model model) {
        return dataService.sendSimpleEmailAndSaveData(data, model);
    }

    /**
     * to see the ticket from client
     * @param id id of ticket
     * @param model to make a page
     * @return a ready ticket page
     */
    @GetMapping("/api/view_ticket/{id}")
    public String viewTicket(@PathVariable int id, Model model) {
        return dataService.getDataById(id, model);
    }
}
