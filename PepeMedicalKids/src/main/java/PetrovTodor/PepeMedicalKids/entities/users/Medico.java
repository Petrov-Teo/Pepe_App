package PetrovTodor.PepeMedicalKids.entities.users;


import PetrovTodor.PepeMedicalKids.enums.Ruolo;
import jakarta.persistence.Column;
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
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "personale_medico")
public class Medico extends User implements UserDetails {
    @Column(unique = true)
    private String codMedico;
    private String specializzazione;
    private String iscrizioneAlboN;
    private boolean passwordTemporanea;

    public Medico(String codMedico, String specializzazione, String iscrizioneAlboN) {
        this.codMedico = codMedico;
        this.specializzazione = specializzazione;
        this.iscrizioneAlboN = iscrizioneAlboN;

    }

    public Medico(String codiceFiscale,
                  String nome,
                  String cognome,
                  LocalDate dataDiNascita,
                  String luogoDiNascita,
                  String email,
                  String password,
                  String numeroDiTelefono,
                  String specializzazione,
                  String iscrizioneAlboN
    ) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, email, password, numeroDiTelefono);
        this.specializzazione = specializzazione;
        this.iscrizioneAlboN = iscrizioneAlboN;
        this.setRuolo(Ruolo.MEDICO);
    }

    public void generaCodice(String ultimoCodice) {
        String primaLetteraRuolo = String.valueOf(this.getRuolo().name().charAt(0));
        int codiceBase = 100;
        this.codMedico = primaLetteraRuolo + codiceBase + ultimoCodice;
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
