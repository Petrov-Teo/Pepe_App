package PetrovTodor.PepeMedicalKids.entities.finanza;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cassa {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idCassa;

    private double importo;
    private String descrizione;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataTransazione;
    private boolean tipoOperazione; // true = Incasso, false = Pagamento
    @OneToMany(mappedBy = "idCassa", cascade = CascadeType.ALL)
    private List<Cassa> transazioni;

    public Cassa(double importo, String descrizione, LocalDate dataTransazione, boolean tipoOperazione) {
        this.importo = importo;
        this.descrizione = descrizione;
        this.dataTransazione = dataTransazione;
        this.tipoOperazione = tipoOperazione;
    }

    // Metodo per calcolare il saldo totale della cassa
    public double calcolaTotaleCassa() {
        return transazioni.stream()
                .mapToDouble(transazione -> transazione.tipoOperazione ? transazione.importo : -transazione.importo)
                .sum();
    }


    public void effettuaIncasso(double importo) {
        this.importo += importo;
    }

    public void effettuaPagamento(double importo) {
        this.importo -= importo;
    }
}
