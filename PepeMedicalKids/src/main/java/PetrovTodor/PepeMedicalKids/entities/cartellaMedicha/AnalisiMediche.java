package PetrovTodor.PepeMedicalKids.entities.cartellaMedicha;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "prescrizione_analisi_mediche")
public class AnalisiMediche extends PrescrizioneMedica {
    private String codAnalisi;
    @OneToMany
    private List<AnalisiRegionali> analisiRegionali;

    @ManyToOne
    @JoinColumn(name = "cartella_id")
    private CartellaMedica cartellaMedica;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false) // Medico che ha scritto il referto
    private Medico medicoAutore;


    public AnalisiMediche(
            TipoPrescrizione tipoPrescrizione,
            String note,
            List<AnalisiRegionali> analisiRegionali,
            Medico medicoAutore) {
        super(tipoPrescrizione, note);
        this.analisiRegionali = analisiRegionali;
        this.medicoAutore = medicoAutore;
    }

    public void generaCodiceAnalisiMediche(String ultimoNumero) {
        String primaLetteraRuolo = String.valueOf(this.getTipoPrescrizione().name().charAt(0));
        int numeroPartenza = 0;
        this.codAnalisi = primaLetteraRuolo + "/" + numeroPartenza + ultimoNumero;
    }
}
