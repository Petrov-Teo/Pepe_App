package PetrovTodor.PepeMedicalKids.entities.cartelleMediche;

import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "analisi_mediche")
public class AnalisiMediche extends PrescrizioneMedica {
    private String codAnalisi;
    private List<AnalisiRegionali> AnalisiRegionali;


    public AnalisiMediche(LocalDate dataPrescrizione, LocalTime ora, TipoPrescrizione tipoPrescrizione, String note, String codAnalisi, List<PetrovTodor.PepeMedicalKids.entities.cartelleMediche.AnalisiRegionali> analisiRegionali) {
        super(dataPrescrizione, ora, tipoPrescrizione, note);
        this.codAnalisi = codAnalisi;
        AnalisiRegionali = analisiRegionali;
    }

    public void setNumCertificato(String ultimoNumero) {
        String primaLetteraRuolo = String.valueOf(this.getTipoPrescrizione().name().charAt(0));
        int numeroPartenza = 0;
        this.codAnalisi = primaLetteraRuolo + "/" + numeroPartenza + ultimoNumero;//Da creare query per la ricerca dell'ultimo codice
    }
}
