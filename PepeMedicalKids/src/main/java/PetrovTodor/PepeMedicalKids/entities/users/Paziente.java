package PetrovTodor.PepeMedicalKids.entities.users;

import PetrovTodor.PepeMedicalKids.enums.Ruolo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "user_pazienti")
public class Paziente extends User {
    @ManyToOne
    private GenitoreTutore genitoreTutore;

    @Column(unique = true)
    private String codPaziente;

    private String note;

    private boolean isMinorenne;

    // COSTRUTTORE SE MAGGIORENNE
    public Paziente(String codiceFiscale,
                    String nome,
                    String cognome,
                    LocalDate dataDiNascita,
                    String luogoDiNascita,
                    String email,
                    String password,
                    long numeroDiTelefono,
                    String note) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, email, password, numeroDiTelefono);
        this.setRuolo(Ruolo.PAZIENTE);
        this.codPaziente = codPaziente;
        this.note = note;
    }

    // COSTRUTTORE SE MINORENNE
    public Paziente(String codiceFiscale,
                    String nome,
                    String cognome,
                    LocalDate dataDiNascita,
                    String luogoDiNascita,
                    GenitoreTutore genitoreTutore,
                    String note) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita);
        this.isMinorenne = calcolaSeMinorenne();
        this.setRuolo(Ruolo.PAZIENTE);
        this.codPaziente = codPaziente;
        this.genitoreTutore = genitoreTutore;
        this.note = note;
    }

    public boolean calcolaSeMinorenne() {
        return Period.between(this.getDataDiNascita(), LocalDate.now()).getYears() < 18;
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
        return this.getEmail();
    }
}
