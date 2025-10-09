package dev.portfolio.contact_api.controller;

import dev.portfolio.contact_api.dto.ContactRequest;
import dev.portfolio.contact_api.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://lucamonteleone-portfolio.netlify.app", "http://localhost:4200"}, 
             allowCredentials = "true",
             maxAge = 3600)
public class ContactController {

    private final MailService mailService;

    @PostMapping
    public ResponseEntity<Void> send(@Valid @RequestBody ContactRequest request) {
        mailService.sendContact(request);
        return ResponseEntity.noContent().build();
    }
}
