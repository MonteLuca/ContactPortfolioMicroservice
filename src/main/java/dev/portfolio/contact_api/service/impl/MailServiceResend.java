package dev.portfolio.contact_api.service.impl;

import dev.portfolio.contact_api.dto.ContactRequest;
import dev.portfolio.contact_api.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Profile("prod")
@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceResend implements MailService {

    private final WebClient webClient;

    @Value("${RESEND_API_KEY}")        String apiKey;
    @Value("${app.mail.to}")           String to;
    @Value("${app.mail.from:onboarding@resend.dev}") String from; // default seguro

    @Override
    public void sendContact(ContactRequest req) {
        var body = Map.of(
                "from", from,
                "to", List.of(to),
                "subject", "[Portfolio] " + req.getSubject(),
                "text", """
                    Nombre: %s
                    Email: %s

                    %s
                    """.formatted(req.getName(), req.getEmail(), req.getMessage())
        );

        webClient.post()
                .uri("/emails")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .onStatus(s -> s.is4xxClientError() || s.is5xxServerError(),
                        r -> r.bodyToMono(String.class).map(msg -> {
                            log.error("Resend error {}: {}", r.statusCode(), msg);
                            return new IllegalStateException("Resend error: " + r.statusCode());
                        }))
                .toBodilessEntity()
                .block();

        log.info("Enviado via Resend a {} con from {}", to, from);
    }
}
