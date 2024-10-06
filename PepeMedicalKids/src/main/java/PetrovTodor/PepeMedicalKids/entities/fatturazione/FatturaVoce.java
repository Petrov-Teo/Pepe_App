package PetrovTodor.PepeMedicalKids.entities.fatturazione;

import PetrovTodor.PepeMedicalKids.enums.Iva;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class FatturaVoce {
    private double netto;
    private Iva iva;

    public FatturaVoce(double netto, Iva iva) {
        this.netto = netto;
        this.iva = iva;
    }

    public double calcolaTotale() {
        return netto + (netto * ((double) iva.getValore() / 100));
    }
}