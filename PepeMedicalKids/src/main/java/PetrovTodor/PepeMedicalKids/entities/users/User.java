package PetrovTodor.PepeMedicalKids.entities.users;


import PetrovTodor.PepeMedicalKids.enums.Ruolo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
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
