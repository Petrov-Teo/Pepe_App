package PetrovTodor.PepeMedicalKids.entities.cartellaMedicha;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "cartelle_mediche")
public class CartellaMedica {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idCartella;
    @Setter(AccessLevel.NONE)
    private String numeroCartella;
    private LocalDate dataCreazione;
    @ManyToMany
    @JoinTable(
            name = "medico_cartella",
            joinColumns = @JoinColumn(name = "idCartella"),
            inverseJoinColumns = @JoinColumn(name = "codMedico")
    )
    private List<Medico> medici;
    @ManyToOne
    @JoinColumn(name = "codPaziente", nullable = false, unique = true)
    private Paziente paziente;
    @OneToMany(mappedBy = "cartellaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnalisiMediche> prescrizioneAnalisi;
    @OneToMany(mappedBy = "cartellaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefertoMedico> prescrizioneReferto;
    @OneToMany(mappedBy = "cartellaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificatoMedico> prescrizioneCertificato;

    public CartellaMedica(Paziente paziente) {
        this.dataCreazione = LocalDate.now();
        this.paziente = paziente;
        this.medici = new ArrayList<>();
    }


    public void creaNumeroCartella(String codicePaziente) {
        this.numeroCartella = "CM/" + codicePaziente;
    }

}
