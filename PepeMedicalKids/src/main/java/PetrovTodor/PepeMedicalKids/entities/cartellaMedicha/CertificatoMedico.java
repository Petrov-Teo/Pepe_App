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
@Table(name = "prescrizione_certificati_medici")
public class CertificatoMedico extends PrescrizioneMedica {
    private String codCertificatoMedico;
    private int giorniPrognosi;
    @ManyToOne
    @JoinColumn(name = "cartella_id")
    private CartellaMedica cartellaMedica;
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false) // Medico che ha scritto il referto
    private Medico medicoAutore;


    public CertificatoMedico(
            TipoPrescrizione tipoPrescrizione,
            String note,
            int giorniPrognosi,
            Medico medicoAutore) {
        super(tipoPrescrizione, note);
        this.giorniPrognosi = giorniPrognosi;
        this.medicoAutore = medicoAutore;
    }

    public void generaCodiceCertificatoMedico(String ultimoNumero) {
        String primaLetteraRuolo = String.valueOf(this.getTipoPrescrizione().name().charAt(0));
        int numeroPartenza = 0;
        this.codCertificatoMedico = primaLetteraRuolo + "/" + numeroPartenza + ultimoNumero;
    }

}
