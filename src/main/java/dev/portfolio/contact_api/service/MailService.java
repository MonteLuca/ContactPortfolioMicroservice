package dev.portfolio.contact_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.to}")
    private String appMailTo;

    @Value("${app.mail.from}")
    private String appMailFrom;

    public void sendContact(String name, String email, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(appMailFrom);
        mail.setTo(appMailTo);
        mail.setSubject("[Portfolio] " + subject);
        mail.setText("Nombre: " + name + "\nEmail: " + email + "\n\n" + message);
        mailSender.send(mail);
        log.info("Contacto enviado a {} con asunto '{}'", appMailTo, subject);
    }
}