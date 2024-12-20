package PetrovTodor.PepeMedicalKids.payload.user;

import PetrovTodor.PepeMedicalKids.enums.TipoTutore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PazienteMinorenneDTO(

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
        String note,
        @NotNull(message = "Il Campo Minorenne S/N di Telefono è Obbligatorio!")
        boolean isMinorenne,
        @NotNull(message = "Il Campo Codice Genitore è Obbligatorio!")
        GenitoreTutoreDTO genitoreTutore,
        
        @NotNull(message = "Il Campo Tipo Tutore/i è obbligatorio!")
        TipoTutore tipoTutore) {
}
