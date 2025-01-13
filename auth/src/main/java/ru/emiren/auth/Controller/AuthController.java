package ru.emiren.auth.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/error")
    public String error() {
        return "";
    }

    @GetMapping("")
    public String index() {
        return "hello, world";
    }

}
