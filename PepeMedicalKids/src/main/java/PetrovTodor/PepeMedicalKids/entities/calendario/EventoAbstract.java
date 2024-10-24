package PetrovTodor.PepeMedicalKids.entities.calendario;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EventoAbstract {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idEvento;
    private String nome;
    private LocalDate dataInizio;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private String note;


    public EventoAbstract(String nome, LocalDate dataInizio, LocalTime oraInizio, LocalTime oraFine, String note) {
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.note = note;
    }
}
