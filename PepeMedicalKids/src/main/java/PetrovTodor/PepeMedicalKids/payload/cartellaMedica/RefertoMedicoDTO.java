package PetrovTodor.PepeMedicalKids.payload.cartellaMedica;

import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefertoMedicoDTO(
        @NotNull(message = "Il Campo Tipo Prescrizione è Obbligatorio!")
        TipoPrescrizione tipoPrescrizione,
        @NotBlank(message = "Il Campo Oggetto è Obbligatorio!")
        String oggetto,
        String note,
        @NotBlank(message = "Il Campo Medico è Obbligatorio!")
        String Medico) {
}
