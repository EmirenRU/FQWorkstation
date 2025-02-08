package ru.emiren.email.Service;

import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<String> sendSimpleMail(String to, String subject, String text);
}
