package PetrovTodor.PepeMedicalKids.payload.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MedicoDTO(
        @NotBlank(message = "Il Campo Codice Fiscale è Obbligatorio!")
        String codiceFiscale,

        @NotBlank(message = "Il Campo Nome è Obbligatorio!")
        String nome,

        @NotBlank(message = "Il Campo Cognome è Obbligatorio!")
        String cognome,

        @NotBlank(message = "Il Campo Data di Nascita è Obbligatorio!")
        LocalDate dataDiNascita,

        @NotBlank(message = "Il Campo Luogo di Nascita è Obbligatorio!")
        String luogoDiNascita,

        @NotBlank(message = "Il Campo e-mail è Obbligatorio!")
        String email,

        @NotBlank(message = "Il Campo Password è Obbligatorio!")
        String password,

        @NotNull(message = "Il Campo Numero di Telefono è Obbligatorio!")
        @Min(10) @Max(10)
        long numeroDiTelefono,

        @NotBlank(message = "Il Campo Specializzazione è Obbligatorio!")
        String specializzazione,

        @NotBlank(message = "Il Campo Iscrizione Albo è Obbligatorio!")
        String iscrizioneAlboN

) {
}
