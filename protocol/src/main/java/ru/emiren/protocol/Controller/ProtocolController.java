package ru.emiren.protocol.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ProtocolController {

    @GetMapping("/")
    public String protocol() {
        return "protocol";
    }
}
