package PetrovTodor.PepeMedicalKids.entities.users;

import PetrovTodor.PepeMedicalKids.enums.Ruolo;
import jakarta.persistence.Entity;
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
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "personale_desk")
public class Receptionist extends User {
    private String codReceptionist;
    private LocalDate dataAssunzione;
    private LocalDate dataTermine;
    private boolean passwordTemporanea;


    public Receptionist(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String email, String password, String numeroDiTelefono) {
        super(
                codiceFiscale,
                nome,
                cognome,
                dataDiNascita,
                luogoDiNascita,
                email,
                password,
                numeroDiTelefono);
        this.dataAssunzione = LocalDate.now();
        this.setRuolo(Ruolo.RECEPTIONIST);
        this.passwordTemporanea = true;
    }

    public void generaCodice(String ultimoCodice) {
        int codiceNumerico = 101; // Valore iniziale predefinito
        if (ultimoCodice != null && !ultimoCodice.isEmpty() && ultimoCodice.length() > 1) {
            String parteNumerica = ultimoCodice.substring(1);
            try {
                codiceNumerico = Integer.parseInt(parteNumerica) + 1;
            } catch (NumberFormatException e) {
                System.err.println("Errore nel parsing del codice numerico: " + e.getMessage());
            }
        }

        char primaLetteraRuolo = this.getRuolo().name().charAt(0);
        this.codReceptionist = primaLetteraRuolo + String.valueOf(codiceNumerico);
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
