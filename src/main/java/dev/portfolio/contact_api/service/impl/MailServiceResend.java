package dev.portfolio.contact_api.service.impl;

import dev.portfolio.contact_api.dto.ContactRequest;
import dev.portfolio.contact_api.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Profile("prod")
@Service
@RequiredArgsConstructor
public class MailServiceResend implements MailService {

    private final WebClient resendWebClient;

    @Value("${RESEND_API_KEY:}") String apiKey;
    @Value("${app.mail.to}")    String to;
    @Value("${app.mail.from}")  String from;

    @Override
    public void sendContact(ContactRequest req) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("RESEND_API_KEY no está configurada. Por favor, configura la variable de entorno RESEND_API_KEY o usa el perfil de desarrollo.");
        }
        
        var body = Map.of(
                "from", from,
                "to", List.of(to),
                "subject", req.getSubject(),
                "text", """
                    Nombre: %s
                    Email: %s

                    %s
                    """.formatted(req.getName(), req.getEmail(), req.getMessage())
        );

        resendWebClient.post()
                .uri("/emails")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
