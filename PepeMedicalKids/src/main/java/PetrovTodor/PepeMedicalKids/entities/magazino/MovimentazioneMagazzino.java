package PetrovTodor.PepeMedicalKids.entities.magazino;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.enums.ZonaUtilizzo;
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
@Table(name = "movimentazione_magazzino")
public class MovimentazioneMagazzino {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idMovimentazioneMagazzino;
    private String codiceMovimentazione;

    @OneToMany(mappedBy = "movimentazioneMagazzino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Articolo> articoli;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataMovimentazione;

    private ZonaUtilizzo zonaUtilizzo;
    @ManyToOne
    @JoinColumn(name = "codMedico")
    private Medico medico;

    private int ultimoAnno = -1;
    private int contatoreProgressivo = 0;

    @ManyToOne
    @JoinColumn(name = "fornitore_id")
    private Fornitore fornitore; // Fornitore associato

    // Costruttore per zone non mediche
    public MovimentazioneMagazzino(List<Articolo> articoli, LocalDate dataMovimentazione, ZonaUtilizzo zonaUtilizzo, Fornitore fornitore) {
        this.articoli = articoli;
        this.dataMovimentazione = dataMovimentazione;
        this.zonaUtilizzo = zonaUtilizzo;
        this.medico = null; // Non necessario
        this.ultimoAnno = -1; // Inizializzazione di default
        this.contatoreProgressivo = 0; // Inizializzazione di default
        this.fornitore = fornitore;
    }

    // Costruttore per zone mediche
    public MovimentazioneMagazzino(List<Articolo> articoli, LocalDate dataMovimentazione, ZonaUtilizzo zonaUtilizzo, Medico medico, Fornitore fornitore) {
        this.articoli = articoli;
        this.dataMovimentazione = dataMovimentazione;
        this.zonaUtilizzo = zonaUtilizzo;
        this.medico = medico; // Necessario solo per la zona MEDICO
        this.ultimoAnno = -1; // Inizializzazione di default
        this.contatoreProgressivo = 0; // Inizializzazione di default
    }

    public void generaCodiceMovimentazione() {
        int annoCorrente = LocalDate.now().getYear();

        // Azzeramento del contatore se l'anno è cambiato
        if (annoCorrente != ultimoAnno) {
            contatoreProgressivo = 0;
            ultimoAnno = annoCorrente;
        }

        contatoreProgressivo++; // Incrementa il contatore

        String annoCifre = String.valueOf(annoCorrente).substring(2); // Ultime due cifre dell'anno
        String numeroProgressivo = String.format("%06d", contatoreProgressivo); // Formatta a 6 cifre

        this.codiceMovimentazione = "MV/" + annoCifre + "/" + numeroProgressivo; // Codice finale
    }

    public void validaUtilizzoMateriale() {
        if (zonaUtilizzo == ZonaUtilizzo.MEDICO && medico == null) {
            throw new IllegalArgumentException("Devi fornire un medico per la zona utilizzo 'MEDICO'.");
        }
    }
}