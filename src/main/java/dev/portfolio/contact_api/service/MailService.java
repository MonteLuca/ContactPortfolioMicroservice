package dev.portfolio.contact_api.service;

import dev.portfolio.contact_api.dto.ContactRequest;

public interface MailService {
    void sendContact(ContactRequest req);
}
