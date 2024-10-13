package PetrovTodor.PepeMedicalKids.entities.fatturazione;

import PetrovTodor.PepeMedicalKids.entities.magazino.Fornitore;
import PetrovTodor.PepeMedicalKids.enums.StatoPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "note_credito_fornitore")
public class NotaCreditoFornitore {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idNotaCredito;

    @OneToOne
    private Fornitore fornitore;

    private String numNotaCredito;

    @ElementCollection
    private List<FatturaVoce> voci = new ArrayList<>();

    private double totaleNotaCredito;
    private StatoPagamento statoPagamento;
    private LocalDate dataNotaCredito;

    private String noteNotaCredito;

    public NotaCreditoFornitore(Fornitore fornitore, String numNotaCredito, List<FatturaVoce> voci, StatoPagamento statoPagamento, LocalDate dataNotaCredito, String noteNotaCredito) {
        this.fornitore = fornitore;
        this.numNotaCredito = numNotaCredito;
        this.voci = voci;
        this.totaleNotaCredito = calcoloTotaleNotaCredito();
        this.statoPagamento = statoPagamento;
        this.dataNotaCredito = dataNotaCredito;
        this.noteNotaCredito = noteNotaCredito;
    }

    public double calcoloTotaleNotaCredito() {
        return voci.stream().mapToDouble(FatturaVoce::calcolaTotale).sum();
    }

    public void applicaNotaCredito() {
        if (this.statoPagamento == StatoPagamento.PAGATA) {
            throw new IllegalArgumentException("La nota di credito è già stata applicata.");
        }
        this.statoPagamento = StatoPagamento.PAGATA;
        // Logica per applicare la nota di credito alla fattura o registrarla in contabilità
    }
}

