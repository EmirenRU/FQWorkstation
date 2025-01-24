package ru.emiren.infosystemdepartment.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    /**
     * @return an index page
     */
    @GetMapping("/")
    public String main(){ return "index"; }

    /**

     * @return an index page
     */
    @GetMapping("/fqw")
    public String fqw(){ return "index"; }

    /**
     * handle for errors in case of something
     * @return a redirect to index
     */
    @GetMapping("/error")
    public String error(){ return "redirect:/"; }

    /**
     * @return a manual page
     */
    @GetMapping("/faq")
    public String faq(){ return "faq"; }

    /**
     * @return I'll never give you up
     */
    @GetMapping("/giveup")
    public String giveup(){ return "redirect:https://youtu.be/dQw4w9WgXcQ?si=_A7-90hHkk9D5RRy"; }

    /**
     * @return a page with console
     */
    @GetMapping("/admin/console")
    public String console(){ return "terminal"; }

}
