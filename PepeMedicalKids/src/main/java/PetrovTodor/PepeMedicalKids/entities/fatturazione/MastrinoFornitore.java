package PetrovTodor.PepeMedicalKids.entities.fatturazione;

import PetrovTodor.PepeMedicalKids.entities.finanza.PagamentoFornitore;
import PetrovTodor.PepeMedicalKids.entities.magazino.Fornitore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mastrini_fornitori")
public class MastrinoFornitore {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idMastrino;

    @OneToOne
    private Fornitore fornitore;

    @ElementCollection
    private List<FatturaPassiva> fatture = new ArrayList<>();

    @ElementCollection
    private List<NotaCreditoFornitore> noteCrediti = new ArrayList<>();

    @ElementCollection
    private List<PagamentoFornitore> pagamenti = new ArrayList<>();

    private double saldoAttuale;

    public MastrinoFornitore(Fornitore fornitore) {
        this.fornitore = fornitore;
        this.saldoAttuale = 0.0;
    }

    public void aggiungiFattura(FatturaPassiva fattura) {
        this.fatture.add(fattura);
        this.saldoAttuale += fattura.getTotaleFattura();
    }

    public void aggiungiNotaCredito(NotaCreditoFornitore notaCredito) {
        this.noteCrediti.add(notaCredito);
        this.saldoAttuale -= notaCredito.getTotaleNotaCredito();
    }

    public void aggiungiPagamento(PagamentoFornitore pagamento) {
        this.pagamenti.add(pagamento);
        this.saldoAttuale -= pagamento.getImporto();
    }
}