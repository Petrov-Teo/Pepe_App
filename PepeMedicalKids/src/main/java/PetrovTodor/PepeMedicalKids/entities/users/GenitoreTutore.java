package PetrovTodor.PepeMedicalKids.entities.users;


import PetrovTodor.PepeMedicalKids.enums.Ruolo;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "user_genitoriTutori")
public class GenitoreTutore extends User {
    private String codGenitore;
    private String note;
    private boolean passwordTemporanea;


    @OneToMany(mappedBy = "genitoreTutore")
    private List<Paziente> pazienti;

    public GenitoreTutore(String codiceFiscale,
                          String nome,
                          String cognome,
                          LocalDate dataDiNascita,
                          String luogoDiNascita,
                          String email,
                          String password,
                          String numeroDiTelefono,
                          String note,
                          List<Paziente> pazienti) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, email, password, numeroDiTelefono);
        this.note = note;
        this.pazienti = pazienti;
        this.setRuolo(Ruolo.GENITORE);
        this.passwordTemporanea = false;
    }

    public void generaCodice(String ultimoCodice) {
        int codiceNumerico = 1001; // Valore iniziale predefinito
        if (ultimoCodice != null && !ultimoCodice.isEmpty() && ultimoCodice.length() > 1) {
            String parteNumerica = ultimoCodice.substring(1);
            try {
                codiceNumerico = Integer.parseInt(parteNumerica) + 1;
            } catch (NumberFormatException e) {
                System.err.println("Errore nel parsing del codice numerico: " + e.getMessage());
            }
        }

        char primaLetteraRuolo = this.getRuolo().name().charAt(0);
        this.codGenitore = primaLetteraRuolo + String.valueOf(codiceNumerico);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((new SimpleGrantedAuthority(this.getRuolo().name())));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

}
