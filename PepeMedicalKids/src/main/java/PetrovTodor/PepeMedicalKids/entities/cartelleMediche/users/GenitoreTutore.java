package PetrovTodor.PepeMedicalKids.entities.cartelleMediche.users;

import PePe.S.r.l.PePe.Medical.Kids.enums.TipoTutore;
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
@Table(name = "genitoriTutori")
public class GenitoreTutore extends User {
    private String codGenitore;
    private String note;
    private TipoTutore tipoTutore;

    @OneToMany(mappedBy = "genitoreTutore")
    private List<Paziente> pazienti;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }
}
