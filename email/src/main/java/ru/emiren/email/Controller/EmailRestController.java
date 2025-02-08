package ru.emiren.email.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.emiren.email.Service.EmailService;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailRestController {

    private final EmailService emailService;

    @Autowired
    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> saveData(String to, @Valid Map<String, String> data, Model model) {
        return emailService.sendSimpleMail(to, data.get("subject"), data.get("text"));
    }
}
