package PetrovTodor.PepeMedicalKids.entities.cartellaMedicha;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "prescrizione_referti_medici")
public class RefertoMedico extends PrescrizioneMedica {

    private String codRefertoMedico;
    private String oggetto;
    @ManyToOne
    @JoinColumn(name = "cartella_id")
    private CartellaMedica cartellaMedica;
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false) // Medico che ha scritto il referto
    private Medico medicoAutore;

    public RefertoMedico(
            TipoPrescrizione tipoPrescrizione,
            String oggetto,
            String note,
            Medico medicoAutore
    ) {
        super(tipoPrescrizione, note);
        this.oggetto = oggetto;
        this.medicoAutore = medicoAutore;
    }

    public void generaCodRefertoMedico(String ultimoNumero) {
        String primaLetteraRuolo = String.valueOf(this.getTipoPrescrizione().name().charAt(0));
        int numeroPartenza = 0;
        this.codRefertoMedico = primaLetteraRuolo + "/" + numeroPartenza + ultimoNumero;//Da creare query per la ricerca dell'ultimo codice
    }
}
