package PetrovTodor.PepeMedicalKids.payload.user;

import jakarta.validation.constraints.Email;

public record LoginRequestDTO(
        @Email(message = "Inserisci un'indirizzo mail valido!")
        String email,
        String password) {
}
