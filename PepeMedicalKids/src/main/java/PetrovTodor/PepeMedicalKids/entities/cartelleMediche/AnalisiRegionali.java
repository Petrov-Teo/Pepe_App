package PetrovTodor.PepeMedicalKids.entities.cartelleMediche;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CurRegionale")
public class AnalisiRegionali {
    @Id
    @GeneratedValue
    private UUID idEsame;
    private String codiceNtrRl;
    private String descrizioneCodiceNtrlRl;
    private String descrizionePadre;
    private String descrizioneFigli;
    private String numeroProgressivo;
    private String cdiceCUR;
    private char compatibilita;
    private int peso;
    private int branca_1;
    private int branca_2;
    private int branca_3;
    private int branca_4;
    private String incompatibilitaConAltriCodici;
    private String inclusi;
    private String noteEcondizioniDiErgabilitaParticolari;
    private String nSeduteCiclo;
    private double tariffaEuro;
    private String stampa;
    private String note;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFine;

    public AnalisiRegionali(String codiceNtrRl, String descrizioneCodiceNtrlRl, String descrizionePadre, String descrizioneFigli, String numeroProgressivo, String cdiceCUR, char compatibilita, int peso, int branca_1, int branca_2, int branca_3, int branca_4, String incompatibilitaConAltriCodici, String inclusi, String noteEcondizioniDiErgabilitaParticolari, String nSeduteCiclo, double tariffaEuro, String stampa, String note, LocalDate dataFine) {
        this.codiceNtrRl = codiceNtrRl;
        this.descrizioneCodiceNtrlRl = descrizioneCodiceNtrlRl;
        this.descrizionePadre = descrizionePadre;
        this.descrizioneFigli = descrizioneFigli;
        this.numeroProgressivo = numeroProgressivo;
        this.cdiceCUR = cdiceCUR;
        this.compatibilita = compatibilita;
        this.peso = peso;
        this.branca_1 = branca_1;
        this.branca_2 = branca_2;
        this.branca_3 = branca_3;
        this.branca_4 = branca_4;
        this.incompatibilitaConAltriCodici = incompatibilitaConAltriCodici;
        this.inclusi = inclusi;
        this.noteEcondizioniDiErgabilitaParticolari = noteEcondizioniDiErgabilitaParticolari;
        this.nSeduteCiclo = nSeduteCiclo;
        this.tariffaEuro = tariffaEuro;
        this.stampa = stampa;
        this.note = note;
        this.dataFine = dataFine;
    }
}
