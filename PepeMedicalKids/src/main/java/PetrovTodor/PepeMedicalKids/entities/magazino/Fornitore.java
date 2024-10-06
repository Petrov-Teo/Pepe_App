package PetrovTodor.PepeMedicalKids.entities.magazino;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "fornitori")
public class Fornitore {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idFornitore;
    private String codiceFornitore;
    private String nome;
    private String indirizzo;
    private List<String> telefono = new ArrayList<>();
    private List<String> email = new ArrayList<>();
    private String codiceFiscale;
    private int partitaIva;


    @OneToMany(mappedBy = "fornitore", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Articolo> articoli = new ArrayList<>();


    public Fornitore(String nome, String indirizzo, List<String> telefono, List<String> email, String codiceFiscale, int partitaIva, List<Articolo> articoli) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.email = email;
        this.codiceFiscale = codiceFiscale;
        this.partitaIva = partitaIva;
        this.articoli = articoli;
    }

    public void aggiungiArticolo(Articolo articolo) {
        articoli.add(articolo);
        articolo.setFornitore(this);
    }

    public void generaCodiceFornitore(int ultimoCodice) {
        // Format the progressive number to 4 digits
        String numeroProgressivo = String.format("%04d", ultimoCodice); // Esempio: 1 diventa "0001"

        // Create the codice fornitore
        this.codiceFornitore = "F/" + numeroProgressivo; // Esempio: "F/0001"
    }
}