package PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "listino_visite")
public class VisitaPrenotabile {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idTipoVisita;
    private String tipo;
    private String descrizione;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "calendario_visita_medico",
            joinColumns = @JoinColumn(name = "visita_id"),
            inverseJoinColumns = @JoinColumn(name = "medico_id")
    )
    private List<Medico> medici; // più medici che fanno lo stesso tipo di visita
    private double prezzo;

    public VisitaPrenotabile(String tipo, String descrizione, List<Medico> medici, double prezzo) {
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.medici = medici;
        this.prezzo = prezzo;
    }
}


