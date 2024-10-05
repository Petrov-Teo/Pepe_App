package PetrovTodor.PepeMedicalKids.entities.cominicazioni;

import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import PetrovTodor.PepeMedicalKids.enums.StatoMessaggio;
import PetrovTodor.PepeMedicalKids.enums.TipoDestinatarioMessaggio;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "messaggi")
public class Messaggi {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idMessaggio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
    private LocalTime ora;
    private String mittente;
    private TipoDestinatarioMessaggio destinatario;
    private String contenuto;
    private UUID rispotaId;
    private StatoMessaggio statoMessaggio;
    @ManyToOne
    private Paziente paziente;

    public Messaggi(String mittente, TipoDestinatarioMessaggio destinatario, String contenuto, UUID rispotaId, StatoMessaggio statoMessaggio, Paziente paziente) {
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.contenuto = contenuto;
        this.rispotaId = rispotaId;
        this.statoMessaggio = statoMessaggio;
        this.paziente = paziente;
    }
}
