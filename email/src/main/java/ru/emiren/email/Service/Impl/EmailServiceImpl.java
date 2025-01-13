package ru.emiren.email.Service.Impl;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import ru.emiren.email.Service.EmailService;

import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;


    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public ResponseEntity<?> sendSimpleMail(String to, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("info-system-no-reply@emiren.com", "Ticket Bot");
            mimeMessageHelper.setTo(to);
            mimeMessage.setSubject(subject);
            mimeMessageHelper.setText(text, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | jakarta.mail.MessagingException | UnsupportedEncodingException ex){
            log.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();

    }
}
