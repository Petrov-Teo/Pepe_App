package PetrovTodor.PepeMedicalKids.payload.calendar;

import PetrovTodor.PepeMedicalKids.enums.TipoRicorrenza;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EventoGenericoDTO(
        @NotBlank(message = "Il camo non deve essere vuoto!")
        String nome,
        @NotNull(message = "Il Campo è obbligatorio!")
        LocalDate dataInizio,
        @NotNull(message = "Il Campo è obbligatorio!")
        LocalTime oraInizio,
        @NotNull(message = "Il Campo è obbligatorio!")
        LocalTime oraFine,
        @NotBlank(message = "Il camo non deve essere vuoto!")
        String note,
        String luogo,
        List<String> partecipanti,
        boolean eventoRicorrente,
        TipoRicorrenza tipoRicorrenza,
        LocalDate dataFineRicorrenza
) {

    // Costruttore per convertire stringhe in LocalDate e LocalTime
    public EventoGenericoDTO(
            String nome,
            String dataInizio, // accetta come stringa
            String oraInizio,   // accetta come stringa
            String oraFine,     // accetta come stringa
            String note,
            String luogo,
            List<String> partecipanti,
            boolean eventoRicorrente,
            String tipoRicorrenza,
            LocalDate dataFineRicorrenza) {

        this(nome,
                LocalDate.parse(dataInizio), // converte stringa in LocalDate
                LocalTime.parse(oraInizio),   // converte stringa in LocalTime
                LocalTime.parse(oraFine),      // converte stringa in LocalTime
                note,
                luogo,
                partecipanti,
                eventoRicorrente,
                TipoRicorrenza.valueOf(tipoRicorrenza),
                dataFineRicorrenza);

        // Validazione per assicurarsi che oraFine sia dopo oraInizio
        if (this.oraFine.isBefore(this.oraInizio)) {
            throw new IllegalArgumentException("L'ora di fine deve essere dopo l'ora di inizio.");
        }
    }
}
