package PetrovTodor.PepeMedicalKids.entities.fatturazione;

import PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili.VisitaPrenotabile;
import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import PetrovTodor.PepeMedicalKids.enums.ModalitàDiPagamento;
import PetrovTodor.PepeMedicalKids.enums.StatoPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "fatture_attive")
public class FatturaAttiva {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idFatturaAttiva;

    private String numFattura;
    private LocalDate dataFattura;
    private LocalTime oraEmissione;

    // Lista delle visite prenotabili associate a questa fattura
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VisitaPrenotabile> visitePrenotabili;


    @OneToOne
    private GenitoreTutore genitoreTutore;

    @ManyToOne
    @JoinColumn(name = "codPaziente", nullable = false)
    private Paziente paziente;

    private double totaleFattura;

    @Enumerated(EnumType.STRING)
    private StatoPagamento statoPagamento;

    @Enumerated(EnumType.STRING)
    private ModalitàDiPagamento modalitaPagamento;

    private LocalDate dataPagamento;
    private String noteFattura;

    private int ultimoAnno = -1;
    private int contatoreProgressivo = 0;


    public FatturaAttiva(List<VisitaPrenotabile> visitePrenotabili, GenitoreTutore genitoreTutore,
                         Paziente paziente, ModalitàDiPagamento modalitaPagamento,
                         LocalDate dataPagamento, String noteFattura) {
        this.numFattura = generaNumeroFattura();
        this.dataFattura = LocalDate.now();
        this.oraEmissione = LocalTime.now();
        this.visitePrenotabili = visitePrenotabili;
        this.genitoreTutore = genitoreTutore;
        this.paziente = paziente; // Aggiunta del paziente
        this.totaleFattura = calcoloTotaleFattura();
        this.statoPagamento = StatoPagamento.IN_ATTESA;
        this.modalitaPagamento = modalitaPagamento;
        this.dataPagamento = dataPagamento;
        this.noteFattura = noteFattura;
    }


    public String generaNumeroFattura() {
        int annoCorrente = LocalDate.now().getYear();
        if (annoCorrente != ultimoAnno) {
            contatoreProgressivo = 0;
            ultimoAnno = annoCorrente;
        }
        contatoreProgressivo++;
        String annoCifre = String.valueOf(annoCorrente).substring(2);
        String numeroProgressivo = String.format("%06d", contatoreProgressivo);
        return "FT/" + annoCifre + "/" + numeroProgressivo;
    }

    public double calcoloTotaleFattura() {
        return this.getVisitePrenotabili().stream().mapToDouble(VisitaPrenotabile::getPrezzo).sum();
    }

    public void elaboraPagamento() {
        if (this.statoPagamento == StatoPagamento.PAGATA) {
            throw new IllegalArgumentException("La fattura è già stata pagata.");
        }
        this.statoPagamento = StatoPagamento.PAGATA;
        this.dataPagamento = LocalDate.now();

    }
    //TODO da spostare nella sezione service
//        // Percorso del file locale
//        String pdfPath = "src/main/resources/fattureAttivePDF/" + numFattura + ".pdf";
//
//        // Genera il PDF e salva localmente
//        generaPDF(pdfPath);
//
//        // Carica il file su Dropbox
//        dropboxUploader.uploadFileToDropbox(pdfPath);
//
//        // (Opzionale) Rimuovi il file locale dopo il caricamento su Dropbox
//        File file = new File(pdfPath);
//        if (file.exists()) {
//            file.delete();
//            System.out.println("File locale eliminato dopo il caricamento su Dropbox.");
//        }
}