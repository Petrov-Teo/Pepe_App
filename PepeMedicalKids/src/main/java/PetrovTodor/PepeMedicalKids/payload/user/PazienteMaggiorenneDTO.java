package PetrovTodor.PepeMedicalKids.payload.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PazienteMaggiorenneDTO(
        @NotBlank(message = "Il Campo Codice Fiscale è Obbligatorio!")
        String codiceFiscale,
        @NotBlank(message = "Il Campo Nome è Obbligatorio!")
        String nome,
        @NotBlank(message = "Il Campo Cognome è Obbligatorio!")
        String cognome,
        @NotNull(message = "Il Campo Data di Nascita è Obbligatorio!")
        LocalDate dataDiNascita,
        @NotBlank(message = "Il Luogo di Nascita  è Obbligatorio!")
        String luogoDiNascita,
        @NotBlank(message = "Il Campo e-mail è Obbligatorio!")
        String email,
        @NotBlank(message = "Il Campo Password è Obbligatorio!")
        String password,
        @NotBlank(message = "Il Campo Numero di Telefono è Obbligatorio!")
        String numeroDiTelefono,
        String note,
        @NotNull(message = "Il Campo Minorenne S/N di Telefono è Obbligatorio!")
        boolean isMinorenne
) {
}
