package PetrovTodor.PepeMedicalKids.entities.finanza;

import PetrovTodor.PepeMedicalKids.entities.fatturazione.FatturaPassiva;
import PetrovTodor.PepeMedicalKids.entities.fatturazione.MastrinoFornitore;
import PetrovTodor.PepeMedicalKids.enums.ModalitàDiPagamento;
import PetrovTodor.PepeMedicalKids.enums.StatoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pagamenti")
public class PagamentoFornitore {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idPagamento;

    @ManyToOne
    private FatturaPassiva fatturaPassiva;
    @ManyToOne
    private MastrinoFornitore mastrinoFornitore;

    private double importo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPagamento;
    @Enumerated(EnumType.STRING)
    private ModalitàDiPagamento modalitàDiPagamento;
    private String descrizione;

    public PagamentoFornitore(FatturaPassiva fatturaPassiva, MastrinoFornitore mastrinoFornitore, double importo, LocalDate dataPagamento, ModalitàDiPagamento modalitàDiPagamento, String descrizione) {
        this.fatturaPassiva = fatturaPassiva;
        this.mastrinoFornitore = mastrinoFornitore;
        this.importo = importo;
        this.dataPagamento = dataPagamento;
        this.modalitàDiPagamento = modalitàDiPagamento;
        this.descrizione = descrizione;
    }

    public void effettuaPagamentoFatturaPassiva(double importo, boolean pagamentoInCassa) {
        if (fatturaPassiva.getStatoPagamento() == StatoPagamento.PAGATA) {
            throw new IllegalArgumentException("La fattura è già stata pagata!");
        }
        if (importo <= 0) {
            throw new IllegalArgumentException("L'importo deve essere maggiore di zero.");
        }
        if (importo > fatturaPassiva.getImportoRimanente()) {
            throw new IllegalArgumentException("L'importo pagato supera l'importo rimanente della fattura.");
        }

        this.mastrinoFornitore.aggiungiPagamento(this);

        fatturaPassiva.aggiornaImportoRimanente(importo);

        // Registrazione del pagamento
        if (pagamentoInCassa) {
            Cassa cassa = new Cassa(importo, descrizione, dataPagamento, false);
        } else {
            Banca banca = new Banca(importo, descrizione, dataPagamento, false);
        }

        if (fatturaPassiva.getImportoRimanente() <= 0) {
            fatturaPassiva.setStatoPagamento(StatoPagamento.PAGATA);
        } else {
            fatturaPassiva.setStatoPagamento(StatoPagamento.IN_ATTESA);
        }
    }
}
