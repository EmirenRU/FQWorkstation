package ru.emiren.email.Service;

import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<?> sendSimpleMail(String to, String subject, String text);
}
