package PetrovTodor.PepeMedicalKids.entities.cartellaMedicha;

import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
