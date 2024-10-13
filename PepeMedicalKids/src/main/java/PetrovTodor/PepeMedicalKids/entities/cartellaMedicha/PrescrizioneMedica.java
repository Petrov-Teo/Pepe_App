package PetrovTodor.PepeMedicalKids.entities.cartellaMedicha;

import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@MappedSuperclass
@Setter
@Getter
@ToString
@NoArgsConstructor
public abstract class PrescrizioneMedica {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.MODULE)
    private UUID idPrescrizione;
    private LocalDate dataPrescrizione;
    private LocalTime oraPrescrizione;
    @Enumerated(EnumType.STRING)
    private TipoPrescrizione tipoPrescrizione;
    private String note;

    public PrescrizioneMedica(TipoPrescrizione tipoPrescrizione, String note) {
        this.dataPrescrizione = LocalDate.now();
        this.oraPrescrizione = LocalTime.now();
        this.tipoPrescrizione = tipoPrescrizione;
        this.note = note;
    }
}
