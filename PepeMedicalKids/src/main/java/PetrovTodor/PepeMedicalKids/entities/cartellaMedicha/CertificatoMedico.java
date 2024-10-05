package PetrovTodor.PepeMedicalKids.entities.cartellaMedicha;

import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public CertificatoMedico(LocalDate dataPrescrizione, LocalTime ora, TipoPrescrizione tipoPrescrizione, String note, String codCertificatoMedico, int giorniPrognosi) {
        super(dataPrescrizione, ora, tipoPrescrizione, note);
        this.codCertificatoMedico = codCertificatoMedico;
        this.giorniPrognosi = giorniPrognosi;
    }

    public void setNumCertificato(String ultimoNumero) {
        String primaLetteraRuolo = String.valueOf(this.getTipoPrescrizione().name().charAt(0));
        int numeroPartenza = 0;
        this.codCertificatoMedico = primaLetteraRuolo + "/" + numeroPartenza + ultimoNumero;//Da creare query per la ricerca dell'ultimo codice
    }
}
