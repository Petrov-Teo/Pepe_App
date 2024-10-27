package PetrovTodor.PepeMedicalKids.entities.cominicazioni;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class RichiestaContatto {
    @Id
    @GeneratedValue
    private UUID idRichiestaComunicazione;
    private String nome;
    private String email;
    private String note;
    private boolean letto;
    private LocalDateTime dataInvio;

    public RichiestaContatto(String nome, String email, String note) {
        this.nome = nome;
        this.email = email;
        this.note = note;
        this.letto = false;
        this.dataInvio = LocalDateTime.now();
    }

    public void changeLetto() {
        this.letto = true;
    }


}
