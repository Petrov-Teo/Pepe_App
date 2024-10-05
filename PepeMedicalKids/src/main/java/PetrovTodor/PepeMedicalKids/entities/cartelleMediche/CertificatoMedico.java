package PetrovTodor.PepeMedicalKids.entities.cartelleMediche;

import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.persistence.Entity;
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
@Table(name = "certificati_medici")
public class CertificatoMedico extends PrescrizioneMedica {
    private String codCertificatoMedico;
    private int giorniPrognosi;

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
