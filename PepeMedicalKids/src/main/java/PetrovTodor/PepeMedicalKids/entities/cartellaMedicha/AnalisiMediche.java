package PetrovTodor.PepeMedicalKids.entities.cartellaMedicha;

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


    public AnalisiMediche(
            TipoPrescrizione tipoPrescrizione,
            String note,
            List<AnalisiRegionali> analisiRegionali) {
        super(tipoPrescrizione, note);
        this.analisiRegionali = analisiRegionali;
    }

    public void setNumCertificato(String ultimoNumero) {
        String primaLetteraRuolo = String.valueOf(this.getTipoPrescrizione().name().charAt(0));
        int numeroPartenza = 0;
        this.codAnalisi = primaLetteraRuolo + "/" + numeroPartenza + ultimoNumero;//Da creare query per la ricerca dell'ultimo codice
    }
}
