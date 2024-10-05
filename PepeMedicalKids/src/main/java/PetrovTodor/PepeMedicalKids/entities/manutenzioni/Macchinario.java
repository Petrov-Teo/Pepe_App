package PetrovTodor.PepeMedicalKids.entities.manutenzioni;

import PetrovTodor.PepeMedicalKids.enums.StatoMacchina;
import PetrovTodor.PepeMedicalKids.enums.StatoManutenzione;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "macchinari")
public class Macchinario {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idMacchinario;

    private String nome;
    private String codMacchinario;
    private String descrizione;
    private boolean contattoAssistenza;

    @OneToMany(mappedBy = "macchinario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContrattoManutenzione> contrattoManutenzione = new ArrayList<>();

    private StatoMacchina statoMacchina;
    private String note;
    private String linkImmagine;

    @ElementCollection
    private List<String> documentazioni = new ArrayList<>();

    @OneToMany(mappedBy = "macchinario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ManutenzioneMacchinario> manutenzioni = new ArrayList<>();

    public Macchinario(String nome, String codMacchinario, String descrizione, boolean contattoAssistenza, StatoMacchina statoMacchina, String note, String linkImmagine) {
        this.nome = nome;
        this.codMacchinario = codMacchinario;
        this.descrizione = descrizione;
        this.contattoAssistenza = contattoAssistenza;
        this.statoMacchina = statoMacchina;
        this.note = note;
        this.linkImmagine = linkImmagine;
    }

    public boolean puòEssereModificato(StatoMacchina nuovoStato) {
        if (haInterventiInCorso()) {
            return false;
        }
        switch (nuovoStato) {
            case IN_FUNZIONA:
            case IN_MANUTENZIONE:
            case DISMESSA:
            case GUASTA:
                return true;
            default:
                return false;
        }
    }

    private boolean haInterventiInCorso() {
        return manutenzioni.stream().anyMatch(manutenzione -> manutenzione.getStatoManutenzione() == StatoManutenzione.IN_CORSO);
    }

    public boolean cambiaStato(StatoMacchina nuovoStato) {
        if (puòEssereModificato(nuovoStato)) {
            this.statoMacchina = nuovoStato;
            return true;
        }
        return false;
    }

    public void generaCodice(int ultimoCodice) {
        this.codMacchinario = nome.replaceAll("\\s+", "_").toUpperCase() + "_" + (ultimoCodice + 1);
    }

    public void aggiungiDocumentazione(String documento) {
        if (documento != null && !documento.trim().isEmpty() && !documentazioni.contains(documento)) {
            documentazioni.add(documento);
        }
    }

    public void rimuoviDocumentazione(String documento) {
        documentazioni.remove(documento);
    }

    // Metodi per gestire contratti di manutenzione
    public void aggiungiContratto(ContrattoManutenzione contratto) {
        contrattoManutenzione.add(contratto);
        contratto.setMacchinario(this);
    }

    public void rimuoviContratto(ContrattoManutenzione contratto) {
        contrattoManutenzione.remove(contratto);
        contratto.setMacchinario(null);
    }

    // Override di equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Macchinario)) return false;
        Macchinario that = (Macchinario) o;
        return idMacchinario.equals(that.idMacchinario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMacchinario);
    }
}