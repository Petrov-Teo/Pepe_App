package PetrovTodor.PepeMedicalKids.entities.magazino;

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
@Table(name = "magazzino_carico")
public class CaricoMagazzino {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idCaricoMagazzino;
    private String codiceCarico;
    @OneToMany(mappedBy = "caricoMagazzino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Articolo> articoli;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCarico;
    private String numeroDocumentoFornitore;
    private int ultimoAnno = -1;
    private int contatoreProgressivo = 0;

    @ManyToOne
    @JoinColumn(name = "codiceFornitore")
    private Fornitore fornitore;

    public CaricoMagazzino(List<Articolo> articoli, LocalDate dataCarico, String numeroDocumentoFornitore, Fornitore fornitore) {
        this.articoli = articoli;
        this.dataCarico = dataCarico;
        this.numeroDocumentoFornitore = numeroDocumentoFornitore;
        this.fornitore = fornitore;
    }


    public void generaCodiceCarico() {
        int annoCorrente = LocalDate.now().getYear();

        if (annoCorrente != ultimoAnno) {
            contatoreProgressivo = 0;
            ultimoAnno = annoCorrente;
        }

        contatoreProgressivo++;

        String annoCifre = String.valueOf(annoCorrente).substring(2);

        String numeroProgressivo = String.format("%06d", contatoreProgressivo);

        this.codiceCarico = "CA/" + annoCifre + "/" + numeroProgressivo;
    }

    public void movimentaCarico() {
        for (Articolo articolo : articoli) {
            int quantitaAggiunta = articolo.getQuantita();
            if (quantitaAggiunta <= 0) {
                throw new IllegalArgumentException("La quantità da aggiungere deve essere maggiore di 0.");
            }
            articolo.aggiornaGiacenza(quantitaAggiunta);
        }
    }
}
