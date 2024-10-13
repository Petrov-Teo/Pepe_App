package PetrovTodor.PepeMedicalKids.entities.finanza;

import PetrovTodor.PepeMedicalKids.entities.fatturazione.FatturaAttiva;
import PetrovTodor.PepeMedicalKids.entities.fatturazione.MastrinoCliente;
import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.enums.ModalitàDiPagamento;
import PetrovTodor.PepeMedicalKids.enums.StatoPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class IncassoFattura {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idIncasso;
    @OneToOne
    private GenitoreTutore genitoreTutore;
    @ManyToOne
    private MastrinoCliente mastrinoCliente;
    private double importo;
    @ManyToOne
    private FatturaAttiva fatturaAttiva;
    private LocalDate dataPagamento;
    @Enumerated(EnumType.STRING)
    private ModalitàDiPagamento modalitàDiPagamento;
    private String descrizione;


    public IncassoFattura(GenitoreTutore genitoreTutore, MastrinoCliente mastrinoCliente, double importo, FatturaAttiva fatturaAttiva, LocalDate dataPagamento, ModalitàDiPagamento modalitàDiPagamento, String descrizione) {
        this.genitoreTutore = genitoreTutore;
        this.mastrinoCliente = mastrinoCliente;
        this.importo = importo;
        this.fatturaAttiva = fatturaAttiva;
        this.dataPagamento = dataPagamento;
        this.modalitàDiPagamento = modalitàDiPagamento;
        this.descrizione = descrizione;
    }

    public void effettuaPagamentoFatturaAttiva(double importo, boolean pagamentoInCassa) {
        if (fatturaAttiva.getStatoPagamento() == StatoPagamento.PAGATA) {
            throw new IllegalArgumentException("La fattura è già stata pagata!");
        }
        if (importo <= 0) {
            throw new IllegalArgumentException("L'importo deve essere maggiore di zero.");
        }
        if (importo > fatturaAttiva.getTotaleFattura()) {
            throw new IllegalArgumentException("L'importo pagato supera il totale della fattura.");
        }
        this.mastrinoCliente.aggiungiIncasso(this);

        // Registrazione dell'incasso
        if (pagamentoInCassa) {
            Cassa cassa = new Cassa(importo, descrizione, dataPagamento, true);
        } else {
            Banca banca = new Banca(importo, descrizione, dataPagamento, true);
        }
        fatturaAttiva.setStatoPagamento(StatoPagamento.PAGATA);
    }
}


