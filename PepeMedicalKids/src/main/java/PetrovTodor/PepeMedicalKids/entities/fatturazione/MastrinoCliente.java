package PetrovTodor.PepeMedicalKids.entities.fatturazione;

import PetrovTodor.PepeMedicalKids.entities.finanza.IncassoFattura;
import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mastrini_clienti")
public class MastrinoCliente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idMastrinoCliente;
    @OneToOne
    private GenitoreTutore genitoreTutore;


    @ManyToOne
    @JoinColumn(name = "codPaziente", nullable = false)
    private Paziente paziente;


    @ElementCollection
    private List<FatturaAttiva> fatture = new ArrayList<>();


    @ElementCollection
    private List<NotaCreditoCliente> notaCreditoCliente = new ArrayList<>();


    @ElementCollection
    private List<IncassoFattura> incassi = new ArrayList<>();


    private double saldoAttuale;


    public MastrinoCliente(GenitoreTutore genitoreTutore, Paziente paziente) {
        this.genitoreTutore = genitoreTutore; // Può essere nullo per pazienti maggiorenni
        this.paziente = paziente;
        this.saldoAttuale = 0.0; // Inizializza il saldo a zero
    }


    public void aggiungiFattura(FatturaAttiva fattura) {
        this.fatture.add(fattura);
        this.saldoAttuale += fattura.getTotaleFattura(); // Aggiorna il saldo
    }

   
    public void aggiungiIncasso(IncassoFattura incasso) {
        this.incassi.add(incasso);
        this.saldoAttuale -= incasso.getImporto(); // Aggiorna il saldo
    }
}
