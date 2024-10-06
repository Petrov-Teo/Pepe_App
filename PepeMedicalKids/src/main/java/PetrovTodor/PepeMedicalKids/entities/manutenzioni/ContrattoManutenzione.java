package PetrovTodor.PepeMedicalKids.entities.manutenzioni;

import PetrovTodor.PepeMedicalKids.entities.magazino.Fornitore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "contratti_di_manutenzione")
public class ContrattoManutenzione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idContrattoManutenzione;
    private String codiceContratto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private int numeroInterventiOrdinari;
    @ManyToOne
    @JoinColumn(name = "codiceFornitore", nullable = false)
    private Fornitore fornitore;

    @ManyToOne
    @JoinColumn(name = "idMacchinario", nullable = false)
    private Macchinario macchinario;

    public ContrattoManutenzione(String codiceContratto, LocalDate dataInizio, LocalDate dataFine, int numeroInterventiOrdinari, Fornitore fornitore, Macchinario macchinario) {
        this.codiceContratto = codiceContratto;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.numeroInterventiOrdinari = numeroInterventiOrdinari;
        this.fornitore = fornitore;
        this.macchinario = macchinario;
    }
}
