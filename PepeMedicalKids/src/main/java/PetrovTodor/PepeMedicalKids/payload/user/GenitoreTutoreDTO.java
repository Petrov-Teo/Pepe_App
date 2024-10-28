package PetrovTodor.PepeMedicalKids.payload.user;

import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record GenitoreTutoreDTO(
        @NotBlank(message = "Il Campo Codice Fiscale è obbligatorio!")
        String codiceFiscale,
        @NotBlank(message = "Il Campo Nome è obbligatorio!")
        String nome,
        @NotBlank(message = "Il Campo Cognome è obbligatorio!")
        String cognome,
        @NotNull(message = "Il Campo Data di Nascita è obbligatorio!")
        LocalDate dataDiNascita,
        @NotBlank(message = "Il Campo Luogo di Nascita è obbligatorio!")
        String luogoDiNascita,
        @NotBlank(message = "Il Campo Email è obbligatorio!")
        String email,
        @NotBlank(message = "Il Campo Password è obbligatorio!")
        String password,
        @NotBlank(message = "Il Campo Numero di Telefono è obbligatorio!")
        String numeroDiTelefono,

        String note,
        @NotBlank(message = "Il Campo Tipo Tutore è obbligatorio!")
        List<Paziente> pazienti) {
}
