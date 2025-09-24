package dev.portfolio.contact_api.controller;

import dev.portfolio.contact_api.dto.ContactRequest;
import dev.portfolio.contact_api.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "${app.cors.origin}")
@RequiredArgsConstructor
public class ContactController {

    private final MailService mailService;

    @PostMapping
    public ResponseEntity<Void> send(@Valid @RequestBody ContactRequest req) {
        mailService.sendContact(req.getName(), req.getEmail(), req.getSubject(), req.getMessage());
        return ResponseEntity.accepted().build();
    }
}
