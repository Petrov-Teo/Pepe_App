package PetrovTodor.PepeMedicalKids.entities.calendario;

import PetrovTodor.PepeMedicalKids.enums.TipoPartecipante;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "calendario_partecipanti")
public class Partecipante {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idPartecipante;
    @Enumerated(EnumType.STRING)
    private TipoPartecipante tipo;


    private String email;
    private String nome;

    // Costruttore, getter e setter
    public Partecipante(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }
}