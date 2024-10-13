package PetrovTodor.PepeMedicalKids.entities.fatturazione;

import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import PetrovTodor.PepeMedicalKids.enums.StatoPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "note_credito_cliente")
public class NotaCreditoCliente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idNotaCredito;
    private String numFattura;
    @OneToOne
    private GenitoreTutore genitoreTutore;
    @ManyToOne
    @JoinColumn(name = "codPaziente", nullable = false)
    private Paziente paziente;
    private LocalDate dataNotaCredito;
    private LocalTime oraEmissione;
    private double totaleNotaCredito;
    @OneToOne
    private FatturaAttiva fatturaAttiva;
    private String noteNotaCredito;
    private int ultimoAnno = -1;
    private int contatoreProgressivo = 0;


    public NotaCreditoCliente(GenitoreTutore genitoreTutore, Paziente paziente, FatturaAttiva fatturaAttiva, String noteNotaCredito) {
        this.genitoreTutore = genitoreTutore;
        this.paziente = paziente;
        this.dataNotaCredito = LocalDate.now();
        this.oraEmissione = LocalTime.now();
        this.fatturaAttiva = fatturaAttiva;
        this.noteNotaCredito = noteNotaCredito;
        this.totaleNotaCredito = calcolaTotaleNotaCredito();
        this.numFattura = generaNumeroNotaDiCredito();
    }

    public String generaNumeroNotaDiCredito() {
        int annoCorrente = LocalDate.now().getYear();
        if (annoCorrente != ultimoAnno) {
            contatoreProgressivo = 0;
            ultimoAnno = annoCorrente;
        }
        contatoreProgressivo++;
        String annoCifre = String.valueOf(annoCorrente).substring(2);
        String numeroProgressivo = String.format("%06d", contatoreProgressivo);
        return "NC/" + annoCifre + "/" + numeroProgressivo;
    }


    public double calcolaTotaleNotaCredito() {

        this.fatturaAttiva.setStatoPagamento(StatoPagamento.NOTA_CREDITO);
        return this.totaleNotaCredito = this.fatturaAttiva.getTotaleFattura();
    }

}
