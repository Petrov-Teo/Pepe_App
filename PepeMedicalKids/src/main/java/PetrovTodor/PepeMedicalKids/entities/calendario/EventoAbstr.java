package PetrovTodor.PepeMedicalKids.entities.calendario;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EventoAbstr {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idEvento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String nome;
    private LocalDate dataInizio;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private String note;


    public EventoAbstr(String nome, LocalDate dataInizio, LocalTime oraInizio, LocalTime oraFine, String note) {
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.note = note;
    }
}
