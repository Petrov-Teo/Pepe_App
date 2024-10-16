package PetrovTodor.PepeMedicalKids.entities.calendario;

import PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili.VisitaPrenotabile;
import PetrovTodor.PepeMedicalKids.enums.TipoRicorrenza;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "calendario_eventi_generici")
public class EventoGenerico extends EventoAbstract {

    private String luogo;
    @ManyToOne()
    private VisitaPrenotabile visitaPrenotabile;
    @ElementCollection
    @CollectionTable(name = "calendario_evento_partecipanti", joinColumns = @JoinColumn(name = "evento_id"))
    private List<Partecipante> partecipanti;
    private boolean eventoRicorrente;
    private TipoRicorrenza tipoRicorrenza;
    private LocalDate dataFineRicorrenza;

    public EventoGenerico(String nome, LocalDate dataInizio, LocalTime oraInizio, LocalTime oraFine, String note, String luogo, VisitaPrenotabile visitaPrenotabile, List<Partecipante> partecipanti, boolean eventoRicorrente, TipoRicorrenza tipoRicorrenza, LocalDate dataFineRicorrenza) {
        super(nome, dataInizio, oraInizio, oraFine, note);
        this.luogo = luogo;
        this.visitaPrenotabile = visitaPrenotabile;
        this.partecipanti = partecipanti;
        this.eventoRicorrente = eventoRicorrente;
        this.tipoRicorrenza = tipoRicorrenza;
        this.dataFineRicorrenza = dataFineRicorrenza;
    }


}
