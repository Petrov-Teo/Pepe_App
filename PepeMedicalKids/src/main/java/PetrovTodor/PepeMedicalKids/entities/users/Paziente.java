package PetrovTodor.PepeMedicalKids.entities.users;

import PetrovTodor.PepeMedicalKids.enums.Ruolo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_pazienti")
public class Paziente extends User {
    @ManyToOne
    public GenitoreTutore genitoreTutore;
    @Column(unique = true)
    private String codPaziente;
    private String note;


    public Paziente(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, Ruolo ruolo, String email, String password, long numeroDiTelefono, GenitoreTutore genitoreTutore, String codPaziente, String note) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, ruolo);
        this.genitoreTutore = genitoreTutore;
        this.codPaziente = codPaziente;
        this.note = note;
    }

    public void generaCodice(String ultimoCodice) {
        String primaLetteraRuolo = String.valueOf(this.getRuolo().name().charAt(0));
        int codiceBase = 100;
        this.codPaziente = primaLetteraRuolo + codiceBase + ultimoCodice;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }
}
