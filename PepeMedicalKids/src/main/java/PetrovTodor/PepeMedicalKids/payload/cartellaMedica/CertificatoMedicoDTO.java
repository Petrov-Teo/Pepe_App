package PetrovTodor.PepeMedicalKids.payload.cartellaMedica;

import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CertificatoMedicoDTO(
        @NotNull(message = "Il Campo Tipo Prescrizione è Obbligatorio")
        TipoPrescrizione tipoPrescrizione,
        String note,
        int giorniPrognosi,
        @NotBlank(message = "Il Campo Certificato Medico è Obbligatorio!")
        String Medico) {
}
