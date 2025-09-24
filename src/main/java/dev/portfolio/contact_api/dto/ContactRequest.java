package dev.portfolio.contact_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactRequest {

    @NotBlank @Size(max = 80)
    private String name;

    @NotBlank @Email @Size(max = 120)
    private String email;

    @NotBlank @Size(max = 120)
    private String subject;

    @NotBlank @Size(max = 2000)
    private String message;

}