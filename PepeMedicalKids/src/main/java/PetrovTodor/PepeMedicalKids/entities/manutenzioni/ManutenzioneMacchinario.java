package PetrovTodor.PepeMedicalKids.entities.manutenzioni;

import PetrovTodor.PepeMedicalKids.enums.StatoMacchina;
import PetrovTodor.PepeMedicalKids.enums.StatoManutenzione;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "manutenzioni_macchinari")
public class ManutenzioneMacchinario {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idManutenzione;

    @OneToOne
    private Macchinario macchinario;

    private String codiceManutenzione;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    private String oggetto;
    private String descrizione;

    // Imposta lo stato iniziale della manutenzione
    private StatoManutenzione statoManutenzione = StatoManutenzione.IN_CORSO;
    private String note;

    public ManutenzioneMacchinario(Macchinario macchinario, String codiceManutenzione, LocalDate dataInizio, String oggetto, String descrizione, String note) {
        if (macchinario == null) {
            throw new IllegalArgumentException("Il macchinario non può essere null.");
        }
        this.macchinario = macchinario;
        this.codiceManutenzione = codiceManutenzione;
        this.dataInizio = dataInizio;
        this.oggetto = oggetto;
        this.descrizione = descrizione;
        this.note = note;

        // Imposta lo stato del macchinario su GUASTA quando inizia la manutenzione
        macchinario.cambiaStato(StatoMacchina.GUASTA);
    }

    public void completaManutenzione() {
        if (macchinario != null) {
            macchinario.cambiaStato(StatoMacchina.IN_FUNZIONA);
        }
        this.dataFine = LocalDate.now();
        this.statoManutenzione = StatoManutenzione.COMPLETATA;
    }

    public void generaCodice(int ultimoCodice) {
        this.codiceManutenzione = "RIP_" + macchinario.getNome().replaceAll("\\s+", "_").toUpperCase() + "_" + (ultimoCodice + 1);
    }
}