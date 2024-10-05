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
import java.util.List;

@Entity
@Table(name = "personale_desk")
@Setter
@Getter
@NoArgsConstructor
public class Receptionist extends User {
    private String codReceptionist;
    private LocalDate dataAssunzione;
    private LocalDate dataTermine;


    public Receptionist(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, Ruolo ruolo, String email, String password, long numeroDiTelefono, String codReceptionist, LocalDate dataAssunzione, LocalDate dataTermine) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, ruolo, email, password, numeroDiTelefono);
        this.codReceptionist = codReceptionist;
        this.dataAssunzione = dataAssunzione;
        this.dataTermine = dataTermine;
    }

    public void generaCodice(String ultimoCodice) {
        String primaLetteraRuolo = String.valueOf(this.getRuolo().name().charAt(0));
        int codiceBase = 100;
        this.codReceptionist = primaLetteraRuolo + "/" + codiceBase + ultimoCodice; // creare query in Reposytory
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