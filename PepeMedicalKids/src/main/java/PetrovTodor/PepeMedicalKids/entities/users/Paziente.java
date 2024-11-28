package PetrovTodor.PepeMedicalKids.entities.users;

import PetrovTodor.PepeMedicalKids.enums.Ruolo;
import PetrovTodor.PepeMedicalKids.enums.TipoTutore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
    @Enumerated(EnumType.STRING)
    private TipoTutore tipoTutore;

    // COSTRUTTORE SE MAGGIORENNE
    public Paziente(String codiceFiscale,
                    String nome,
                    String cognome,
                    LocalDate dataDiNascita,
                    String luogoDiNascita,
                    String email,
                    String password,
                    String numeroDiTelefono,
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
                    TipoTutore tipoTutore,
                    String note) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita);
        this.isMinorenne = calcolaSeMinorenne();
        this.setRuolo(Ruolo.PAZIENTE);
        this.codPaziente = codPaziente;
        this.genitoreTutore = genitoreTutore;
        this.tipoTutore = tipoTutore;
        this.note = note;
    }

    public boolean calcolaSeMinorenne() {
        return Period.between(this.getDataDiNascita(), LocalDate.now()).getYears() < 18;
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
        this.codPaziente = primaLetteraRuolo + String.valueOf(codiceNumerico);
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
