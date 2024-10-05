package PetrovTodor.PepeMedicalKids.entities.users;


import PetrovTodor.PepeMedicalKids.enums.Ruolo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "Amministratori")
@Setter
@Getter
@NoArgsConstructor
public class Admin extends User {
    private String codAdmin;

    public Admin(String codiceFiscale,
                 String nome,
                 String cognome,
                 LocalDate dataDiNascita,
                 String luogoDiNascita,
                 Ruolo ruolo,
                 String email,
                 String password,
                 long numeroDiTelefono,
                 String codAdmin) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, ruolo, email, password, numeroDiTelefono);
        this.codAdmin = codAdmin;
    }

    public void generaCodice(String ultimoCodice) {
        String primaLetteraRuolo = String.valueOf(this.getRuolo().name().charAt(0));
        int codiceBase = 100;
        this.codAdmin = primaLetteraRuolo + "/" + codiceBase + ultimoCodice;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((new SimpleGrantedAuthority(this.getRuolo().name())));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }
}
