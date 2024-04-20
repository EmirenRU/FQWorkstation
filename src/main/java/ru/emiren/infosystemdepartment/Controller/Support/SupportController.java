package ru.emiren.infosystemdepartment.Controller.Support;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Model.Support.Data;
import ru.emiren.infosystemdepartment.Repository.Support.DataRepository;
import ru.emiren.infosystemdepartment.Service.Support.DataService;

@Controller
public class SupportController {
    private final DataService dataService;
    private final DataRepository dataRepository;

    @Autowired
    public SupportController(DataService dataService, DataRepository dataRepository) {
        this.dataService = dataService;
        this.dataRepository = dataRepository;
    }

    @GetMapping("/support")
    public String main(Model model) {
        Data data = new Data();
        model.addAttribute("data", data);
        return "support";
    }

    @PostMapping("/support")
    public String saveData(@Valid @ModelAttribute("data") DataDTO data, Model model) {
        dataService.saveData(data);

        return "redirect:/support";
    }
}
