package PetrovTodor.PepeMedicalKids.entities.cartelleMediche;

import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "referti_medici")
public class RefertoMedico extends PrescrizioneMedica {

    private String codRefertoMedico;
    private String oggetto;
    @ManyToOne
    @JoinColumn(name = "cartella_id")
    private CartellaMedica cartellaMedica;


    public RefertoMedico(LocalDate dataPrescrizione, LocalTime ora, TipoPrescrizione tipoPrescrizione, String note, String codRefertoMedico, String oggetto) {
        super(dataPrescrizione, ora, tipoPrescrizione, note);
        this.codRefertoMedico = codRefertoMedico;
        this.oggetto = oggetto;
    }

    public void setNumCertificato(String ultimoNumero) {
        String primaLetteraRuolo = String.valueOf(this.getTipoPrescrizione().name().charAt(0));
        int numeroPartenza = 0;
        this.codRefertoMedico = primaLetteraRuolo + "/" + numeroPartenza + ultimoNumero;//Da creare query per la ricerca dell'ultimo codice
    }
}
