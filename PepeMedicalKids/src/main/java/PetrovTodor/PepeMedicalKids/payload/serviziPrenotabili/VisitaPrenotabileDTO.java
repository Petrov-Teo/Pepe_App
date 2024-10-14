package PetrovTodor.PepeMedicalKids.payload.serviziPrenotabili;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VisitaPrenotabileDTO(
        @NotBlank(message = "Il Campo Tipo è obbligatoria!")
        String tipo,
        @NotBlank(message = "Il Campo Descrizioni è obbligatoria!")
        String descrizione,
        @NotNull(message = "Il Campo Medico/Medici è obbligatoria!")
        String medico,
        @NotNull(message = "Il Campo tipo è obbligatoria!")
        double prezzo) {
}
