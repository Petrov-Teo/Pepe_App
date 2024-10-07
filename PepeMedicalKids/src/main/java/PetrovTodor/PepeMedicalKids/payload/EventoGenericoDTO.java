package PetrovTodor.PepeMedicalKids.payload;

import PetrovTodor.PepeMedicalKids.entities.calendario.Partecipante;
import PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili.VisitaPrenotabile;
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
        VisitaPrenotabile visitaPrenotabile,
        List<Partecipante> partecipanti,
        boolean eventoRiccorrente,
        TipoRicorrenza tipoRicorrenza,
        LocalDate dataFineRiccorenza
) {
}
