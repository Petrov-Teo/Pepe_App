package PetrovTodor.PepeMedicalKids.payload.comunicazioni;

import jakarta.validation.constraints.NotBlank;

public record RichiestaContattoDTO(
        @NotBlank(message = "Il Campo nome è obbligatorio!")
        String nome,
        @NotBlank(message = "Il Campo email è obbligatorio!")
        String email,
        @NotBlank(message = "Il Campo note è obbligatorio!")
        String note
) {
}
