package PetrovTodor.PepeMedicalKids.payload.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordResetDTO(
        @NotBlank(message = "Il Campo Vecchia Password è Obbligatorio!")
        String oldPassword,

        @NotBlank(message = "Il Campo Nuova Password è Obbligatorio!")
        @Size(min = 8, message = "La password deve essere lunga almeno 8 caratteri")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                message = "La nuova password deve contenere almeno un numero, una lettera maiuscola e un carattere speciale (@#$%^&+=!)"
        )
        String newPassword) {
}
