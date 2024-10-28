package ru.emiren.infosystemdepartment.Service.Email.Impl;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Service.Email.EmailService;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail(String to, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("info-system-no-reply@emiren.com", "Ticket Bot");
            mimeMessageHelper.setTo(to);
            mimeMessage.setSubject(subject);
            mimeMessageHelper.setText(text, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException ex){
            ex.printStackTrace();
        } catch (jakarta.mail.MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }
}
