package ru.emiren.infosystemdepartment.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String main(){ return "index"; }

    @GetMapping("/faq")
    public String faq(){ return "faq"; }

}
