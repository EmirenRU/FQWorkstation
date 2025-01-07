package ru.emiren.infosystemdepartment.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(){ return "index"; }

    @GetMapping("/fqw")
    public String fqw(){ return "index"; }

    @GetMapping("/error")
    public String error(){ return "redirect:/"; }

    @GetMapping("/faq")
    public String faq(){ return "faq"; }

    @GetMapping("/giveup")
    public String giveup(){ return "redirect:https://youtu.be/dQw4w9WgXcQ?si=_A7-90hHkk9D5RRy"; }

    @GetMapping("/admin/console")
    public String console(){ return "terminal"; }

}
