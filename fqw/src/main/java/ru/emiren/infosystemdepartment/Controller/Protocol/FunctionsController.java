package ru.emiren.infosystemdepartment.Controller.Protocol;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/functions")
@Controller
@Slf4j
public class FunctionsController {
    @GetMapping("")
    public String protocol() {
            return "functions";
        }

}
