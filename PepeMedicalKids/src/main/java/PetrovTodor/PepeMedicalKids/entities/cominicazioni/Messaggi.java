package PetrovTodor.PepeMedicalKids.entities.cominicazioni;

import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import PetrovTodor.PepeMedicalKids.enums.StatoMessaggio;
import PetrovTodor.PepeMedicalKids.enums.TipoDestinatarioMessaggio;
import jakarta.persistence.*;
import lombok.*;

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
