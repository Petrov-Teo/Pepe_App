package PetrovTodor.PepeMedicalKids.entities.cartelleMediche.users;


import PePe.S.r.l.PePe.Medical.Kids.enums.Ruolo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
@Setter
@Getter
@ToString
@NoArgsConstructor
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idUtente;
    @Column(unique = true)
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private String luogoDiNascita;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;
    private String email;
    private String password;
    private long numeroDiTelefono;

    public User(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, Ruolo ruolo, String email, String password, long numeroDiTelefono) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.luogoDiNascita = luogoDiNascita;
        this.ruolo = ruolo;
        this.email = email;
        this.password = password;
        this.numeroDiTelefono = numeroDiTelefono;
    }
}
