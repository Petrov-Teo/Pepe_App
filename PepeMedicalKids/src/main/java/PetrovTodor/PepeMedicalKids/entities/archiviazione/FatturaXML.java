package PetrovTodor.PepeMedicalKids.entities.archiviazione;

import PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili.VisitaPrenotabile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@XmlRootElement(name = "fattura")
public class FatturaXML {
    private String numFattura;
    private LocalDate dataFattura;
    private LocalTime oraEmissione;
    private double totaleFattura;
    private String statoPagamento;
    private String modalitaPagamento;
    private List<VisitaPrenotabile> visitePrenotabili;

    // Costruttore vuoto per JAXB
    public FatturaXML() {
    }

    // Costruttore con parametri
    public FatturaXML(String numFattura, LocalDate dataFattura, LocalTime oraEmissione, double totaleFattura,
                      String statoPagamento, String modalitaPagamento, List<VisitaPrenotabile> visitePrenotabili) {
        this.numFattura = numFattura;
        this.dataFattura = dataFattura;
        this.oraEmissione = oraEmissione;
        this.totaleFattura = totaleFattura;
        this.statoPagamento = statoPagamento;
        this.modalitaPagamento = modalitaPagamento;
        this.visitePrenotabili = visitePrenotabili;
    }

    @XmlElement
    public String getNumFattura() {
        return numFattura;
    }

    public void setNumFattura(String numFattura) {
        this.numFattura = numFattura;
    }

    @XmlElement
    public LocalDate getDataFattura() {
        return dataFattura;
    }

    public void setDataFattura(LocalDate dataFattura) {
        this.dataFattura = dataFattura;
    }

    @XmlElement
    public LocalTime getOraEmissione() {
        return oraEmissione;
    }

    public void setOraEmissione(LocalTime oraEmissione) {
        this.oraEmissione = oraEmissione;
    }

    @XmlElement
    public double getTotaleFattura() {
        return totaleFattura;
    }

    public void setTotaleFattura(double totaleFattura) {
        this.totaleFattura = totaleFattura;
    }

    @XmlElement
    public String getStatoPagamento() {
        return statoPagamento;
    }

    public void setStatoPagamento(String statoPagamento) {
        this.statoPagamento = statoPagamento;
    }

    @XmlElement
    public String getModalitaPagamento() {
        return modalitaPagamento;
    }

    public void setModalitaPagamento(String modalitaPagamento) {
        this.modalitaPagamento = modalitaPagamento;
    }

    @XmlElement(name = "visite")
    public List<VisitaPrenotabile> getVisitePrenotabili() {
        return visitePrenotabili;
    }

    public void setVisitePrenotabili(List<VisitaPrenotabile> visitePrenotabili) {
        this.visitePrenotabili = visitePrenotabili;
    }

    public void generaXML(String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(FatturaXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(this, new File(path)); // Usa 'this' per marshalling l'oggetto esistente
            System.out.println("XML generato con successo!");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}