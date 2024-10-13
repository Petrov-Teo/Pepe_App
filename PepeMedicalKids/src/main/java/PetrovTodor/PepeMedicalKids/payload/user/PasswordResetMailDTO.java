package PetrovTodor.PepeMedicalKids.payload.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetMailDTO(
        @NotBlank(message = "Il campo Email è obbligatorio!")
        @Email(message = "Email non valida!")
        String email) {
}
