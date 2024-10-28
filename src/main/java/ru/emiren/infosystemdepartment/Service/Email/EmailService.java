package ru.emiren.infosystemdepartment.Service.Email;

public interface EmailService {
    void sendSimpleMail(String to, String subject, String text);
}
