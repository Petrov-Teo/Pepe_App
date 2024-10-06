package PetrovTodor.PepeMedicalKids.entities.magazino;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.enums.ZonaUtilizzo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private List<Articolo> articoli = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataMovimentazione;
    @Enumerated(EnumType.STRING)
    private ZonaUtilizzo zonaUtilizzo;

    @ManyToOne
    @JoinColumn(name = "codMedico")
    private Medico medico;

    private int ultimoAnno = -1;
    private int contatoreProgressivo = 0;

    public MovimentazioneMagazzino(List<Articolo> articoli, LocalDate dataMovimentazione, ZonaUtilizzo zonaUtilizzo) {
        this.articoli = articoli;
        this.dataMovimentazione = dataMovimentazione;
        this.zonaUtilizzo = zonaUtilizzo;
        this.medico = null;
        this.ultimoAnno = -1;
        this.contatoreProgressivo = 0;
    }

    // Costruttore per zone mediche
    public MovimentazioneMagazzino(List<Articolo> articoli, LocalDate dataMovimentazione, ZonaUtilizzo zonaUtilizzo, Medico medico) {
        this.articoli = articoli;
        this.dataMovimentazione = dataMovimentazione;
        this.zonaUtilizzo = zonaUtilizzo;
        this.medico = medico;
        this.ultimoAnno = -1;
        this.contatoreProgressivo = 0;
    }

    public void generaCodiceMovimentazione() {
        int annoCorrente = LocalDate.now().getYear();
        if (annoCorrente != ultimoAnno) {
            contatoreProgressivo = 0;
            ultimoAnno = annoCorrente;
        }
        contatoreProgressivo++;
        String annoCifre = String.valueOf(annoCorrente).substring(2);
        String numeroProgressivo = String.format("%06d", contatoreProgressivo);
        this.codiceMovimentazione = "MV/" + annoCifre + "/" + numeroProgressivo;
    }

    public void validaUtilizzoMateriale() {
        if (zonaUtilizzo == ZonaUtilizzo.MEDICO && medico == null) {
            throw new IllegalArgumentException("Devi fornire un medico per la zona utilizzo 'MEDICO'.");
        }
    }

    public void movimentaScarico() {
        for (Articolo articolo : articoli) {
            int quantitaUsata = articolo.getQuantita();
            if (quantitaUsata <= 0) {
                throw new IllegalArgumentException("La quantità da utilizzare deve essere maggiore di 0.");
            }
            articolo.decrementaGiacenza(quantitaUsata);
        }
    }
}