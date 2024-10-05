package PetrovTodor.PepeMedicalKids.entities.users;


import PetrovTodor.PepeMedicalKids.enums.TipoTutore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_genitoriTutori")
public class GenitoreTutore extends User {
    private String codGenitore;
    private String note;
    private TipoTutore tipoTutore;

    @OneToMany(mappedBy = "genitoreTutore")
    private List<Paziente> pazienti;


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
