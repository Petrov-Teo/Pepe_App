package PetrovTodor.PepeMedicalKids.entities.magazino;

import PetrovTodor.PepeMedicalKids.enums.TipoArticolo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "articoli")
public class Articolo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idArticolo;
    private String codiceArticolo;
    private String nome;
    private String descrizione;
    private TipoArticolo tipoArticolo;
    private int quantita;
    private int giacenza;
    private boolean liquido;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataScadenza;

    private double costoAcquisto;


    @ManyToOne
    @JoinColumn(name = "fornitore_id")
    private Fornitore fornitore;

    // Costruttore
    public Articolo(String nome, String descrizione, TipoArticolo tipoArticolo, int quantita, int giacenza, boolean liquido, LocalDate dataScadenza, double costoAcquisto, Fornitore fornitore) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.tipoArticolo = tipoArticolo;
        this.quantita = quantita;
        this.giacenza = giacenza;
        this.liquido = liquido;
        this.dataScadenza = dataScadenza;
        this.costoAcquisto = costoAcquisto;
        this.fornitore = fornitore;
    }


    public void aggiornaGiacenza(int quantitaAggiunta) {
        if (quantitaAggiunta < 0) {
            throw new IllegalArgumentException("Quantità aggiunta non può essere negativa.");
        }
        this.giacenza += quantitaAggiunta;
    }


    public void decrementaGiacenza(int quantitaUsata) {
        if (quantitaUsata <= 0) {
            throw new IllegalArgumentException("Quantità usata deve essere maggiore di 0.");
        }
        if (this.giacenza >= quantitaUsata) {
            this.giacenza -= quantitaUsata;
        } else {
            throw new IllegalArgumentException("Quantità usata supera la giacenza disponibile.");
        }
    }

    public void generaCodice(int ultimoCodice) {

        String primeDueLettereTipoArticolo = this.tipoArticolo.name().substring(0, 2).toUpperCase();

        String numeroProgressivo = String.format("%07d", ultimoCodice);

        this.codiceArticolo = primeDueLettereTipoArticolo + "/" + numeroProgressivo;
    }
}