package dev.portfolio.contact_api.service.impl;

import dev.portfolio.contact_api.dto.ContactRequest;
import dev.portfolio.contact_api.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
@RequiredArgsConstructor
public class MailServiceSmtp implements MailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.to}")   String to;
    @Value("${app.mail.from}") String from;

    @Override
    public void sendContact(ContactRequest req) {
        var msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setFrom(from);
        msg.setSubject(req.getSubject());
        msg.setText("""
                Nombre: %s
                Email: %s

                %s
                """.formatted(req.getName(), req.getEmail(), req.getMessage()));
        mailSender.send(msg);
    }
}
