package ru.emiren.infosystemdepartment.Controller.Protocol;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/functions")
@Controller
public class FunctionsController {

    @GetMapping("")
    public String protocol() {
        return "functions";
    }
}
