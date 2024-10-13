package PetrovTodor.PepeMedicalKids.payload.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import jakarta.validation.constraints.NotNull;

public record CartellaMedicaDTO(
        @NotNull(message = "Il Campo paziente è obbligatorio!")
        Paziente paziente


) {
}
