package PetrovTodor.PepeMedicalKids.entities.users;


import PetrovTodor.PepeMedicalKids.enums.Ruolo;
import PetrovTodor.PepeMedicalKids.enums.TipoTutore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

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
    private TipoTutore tipoTutore;

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
                          TipoTutore tipoTutore,
                          List<Paziente> pazienti) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, email, password, numeroDiTelefono);
        this.note = note;
        this.tipoTutore = tipoTutore;
        this.pazienti = pazienti;
        this.setRuolo(Ruolo.GENITORE);
    }

    public void generaCodice(String ultimoCodice) {
        String primaLetteraRuolo = String.valueOf(this.getRuolo().name().charAt(0));
        int codiceBase = 100;
        this.codGenitore = primaLetteraRuolo + codiceBase + ultimoCodice;
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
