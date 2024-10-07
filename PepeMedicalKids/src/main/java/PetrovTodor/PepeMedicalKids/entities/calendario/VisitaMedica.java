package PetrovTodor.PepeMedicalKids.entities.calendario;

import PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili.VisitaPrenotabile;
import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "calendario_visite_mediche")
public class VisitaMedica extends EventoAbstr {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visita_prenotabile_id")
    private VisitaPrenotabile visitaPrenotabile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codMedico")
    private Medico medico;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codPaziente")
    private Paziente paziente;

    public VisitaMedica(
            String nome,
            LocalDate dataInizio,
            LocalTime oraInizio,
            LocalTime oraFine,
            String note,
            VisitaPrenotabile visitaPrenotabile,
            Medico medico,
            Paziente paziente) {
        super(nome, dataInizio, oraInizio, oraFine, note);
        this.visitaPrenotabile = visitaPrenotabile;
        this.medico = medico;
        this.paziente = paziente;
    }

    public GenitoreTutore getGenitoreDelPaziente() {
        return paziente.getGenitoreTutore();
    }
}
