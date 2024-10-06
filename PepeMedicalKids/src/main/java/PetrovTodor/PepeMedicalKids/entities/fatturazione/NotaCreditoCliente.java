package PetrovTodor.PepeMedicalKids.entities.fatturazione;

import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.enums.StatoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNotaCredito;

    private LocalTime oraEmissione;
    private double totaleNotaCredito;
    @OneToOne
    private FatturaAttiva fatturaAttiva;
    private String noteNotaCredito;

    private int ultimoAnno = -1;
    private int contatoreProgressivo = 0;

    public NotaCreditoCliente(GenitoreTutore genitoreTutore, FatturaAttiva fatturaAttiva, String noteNotaCredito) {
        this.genitoreTutore = genitoreTutore;
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
