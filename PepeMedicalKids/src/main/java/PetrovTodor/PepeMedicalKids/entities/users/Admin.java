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
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "personale_amministrativo")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Admin extends User implements UserDetails {
    private String codAdmin;

    public Admin(String codiceFiscale,
                 String nome,
                 String cognome,
                 LocalDate dataDiNascita,
                 String luogoDiNascita,
                 String email,
                 String password,
                 String numeroDiTelefono
    ) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, email, password, numeroDiTelefono);
        this.setRuolo(Ruolo.ADMIN);
    }

    public void generaCodice(String ultimoCodice) {
        int codiceNumerico = 101;
        if (ultimoCodice != null) {
            String parteNumerica = ultimoCodice.substring(1);
            codiceNumerico = Integer.parseInt(parteNumerica) + 1;
        }
        char primaLetteraRuolo = this.getRuolo().name().charAt(0);
        this.codAdmin = primaLetteraRuolo + String.valueOf(codiceNumerico);
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
