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
@Table(name = "fatture_passive")
public class FatturaPassiva {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idFatturaPassiva;

    @OneToOne
    private Fornitore fornitore;

    private String numFattura;
    @ElementCollection
    private List<FatturaVoce> voci = new ArrayList<>();

    private double totaleFattura;
    private double importoRimanente;
    private StatoPagamento statoPagamento;
    private LocalDate dataFattura;
    private LocalDate dataPagamento;

    private String noteFattura;

    public FatturaPassiva(Fornitore fornitore, String numFattura, List<FatturaVoce> voci, StatoPagamento statoPagamento, LocalDate dataFattura, LocalDate dataPagamento, String noteFattura) {
        this.fornitore = fornitore;
        this.numFattura = numFattura;
        this.voci = voci;
        this.totaleFattura = calcoloTotaleFattura();
        this.importoRimanente = this.totaleFattura; // Inizializza con il totale della fattura
        this.statoPagamento = statoPagamento;
        this.dataFattura = dataFattura;
        this.dataPagamento = dataPagamento;
        this.noteFattura = noteFattura;
    }

    public double calcoloTotaleFattura() {
        return voci.stream().mapToDouble(FatturaVoce::calcolaTotale).sum();
    }

    public void aggiornaImportoRimanente(double pagamento) {
        this.importoRimanente -= pagamento; // Aggiorna l'importo rimanente
        if (this.importoRimanente <= 0) {
            this.importoRimanente = 0; // Assicurati che non diventi negativo
            this.statoPagamento = StatoPagamento.PAGATA; // Imposta lo stato a PAGATA se è tutto pagato
        } else {
            this.statoPagamento = StatoPagamento.IN_ATTESA; // Resta in attesa se ci sono pagamenti rimanenti
        }
    }


}
