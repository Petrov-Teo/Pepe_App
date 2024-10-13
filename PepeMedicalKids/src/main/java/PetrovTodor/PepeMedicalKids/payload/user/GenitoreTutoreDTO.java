package PetrovTodor.PepeMedicalKids.payload.user;

import jakarta.validation.constraints.NotBlank;

public record GenitoreTutoreDTO(
        @NotBlank(message = "Il Campo Nome è obbligatorio!")
        String nome,
        @NotBlank(message = "Il Campo Cognome è obbligatorio!")
        String cognome,
        String note,
        @NotBlank(message = "Il Campo Tipo Tutore è obbligatorio!")
        String tipoTutore) {
}
