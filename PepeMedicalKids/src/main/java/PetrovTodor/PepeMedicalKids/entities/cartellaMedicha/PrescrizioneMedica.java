package PetrovTodor.PepeMedicalKids.entities.cartellaMedicha;

import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
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
@Setter
@Getter
@ToString
@NoArgsConstructor
public abstract class PrescrizioneMedica {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.MODULE)
    private UUID idPrescrizione;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPrescrizione;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")  // Formato ora HH:mm
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime oraPrescrizione;
    private TipoPrescrizione tipoPrescrizione;
    private String note;

    public PrescrizioneMedica(LocalDate dataPrescrizione, LocalTime ora, TipoPrescrizione tipoPrescrizione, String note) {
        this.dataPrescrizione = dataPrescrizione != null ? dataPrescrizione : LocalDate.now();
        this.oraPrescrizione = ora != null ? ora : LocalTime.now();
        this.tipoPrescrizione = tipoPrescrizione;
        this.note = note;
    }
}
