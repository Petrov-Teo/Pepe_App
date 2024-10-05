package PetrovTodor.PepeMedicalKids.entities.cartelleMediche;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cartelle_mediche")
public class CartellaMedica {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idCartella;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCreazione;

    @ManyToOne
    @JoinColumn(referencedColumnName = "codMedico", nullable = false)
    private Medico medico;
    @ManyToOne
    @JoinColumn(name = "codPaziente", nullable = false)
    private Paziente paziente;

    @OneToMany(mappedBy = "cartellaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnalisiMediche> prescrizioneAnalisi;
    @OneToMany(mappedBy = "cartellaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefertoMedico> prescrizioneReferto;
    @OneToMany(mappedBy = "cartellaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificatoMedico> prescrizioneCertificato;

    public CartellaMedica(Medico medico, Paziente paziente) {
        this.dataCreazione = LocalDate.now();
        this.medico = medico;
        this.paziente = paziente;
    }
}
