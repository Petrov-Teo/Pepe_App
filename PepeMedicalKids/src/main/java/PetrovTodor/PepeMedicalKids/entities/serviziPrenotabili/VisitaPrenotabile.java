package PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "listino_visite")
public class VisitaPrenotabile {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idTipoVisita;
    private String tipo;
    private String descrizione;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "medico_id")
    private Medico medico; // più medici che fanno lo stesso tipo di visita

    private double prezzo;

    public VisitaPrenotabile(
            String tipo,
            String descrizione,
            Medico medico,
            double prezzo
    ) {
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.medico = medico;
        this.prezzo = prezzo;
    }
}


