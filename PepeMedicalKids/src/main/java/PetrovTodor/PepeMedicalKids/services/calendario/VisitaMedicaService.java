package PetrovTodor.PepeMedicalKids.services.calendario;

import PetrovTodor.PepeMedicalKids.entities.calendario.VisitaMedica;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.repositorys.calendario.VisitaMedicaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class VisitaMedicaService {
    @Autowired
    private VisitaMedicaRepository visitaMedicaRepository;

    public VisitaMedica findVisitaMedicaById(UUID idVisitaMedica) {
        return this.visitaMedicaRepository.findById(idVisitaMedica).
                orElseThrow(() -> new NotFoundException("Visita Medica con ID " + idVisitaMedica + " non trovato."));
    }

    public VisitaMedica save(VisitaMedica body) {
        return this.visitaMedicaRepository.save(body);
    }

    public VisitaMedica findAndUpdate(UUID idVisitaMedica, VisitaMedica body) {
        VisitaMedica visitaMedicaFound = findVisitaMedicaById(idVisitaMedica);
        visitaMedicaFound.setNome(body.getNome());
        visitaMedicaFound.setDataInizio(body.getDataInizio());
        visitaMedicaFound.setOraInizio(body.getOraInizio());
        visitaMedicaFound.setOraFine(body.getOraFine());
        visitaMedicaFound.setNote(body.getNote());
        visitaMedicaFound.setVisitaPrenotabile(body.getVisitaPrenotabile());
        visitaMedicaFound.setMedico(body.getMedico());
        visitaMedicaFound.setPaziente(body.getPaziente());
        return this.visitaMedicaRepository.save(visitaMedicaFound);
    }

    public VisitaMedica findByPaziente(String name) {
        List<VisitaMedica> visitaMedicaFound = this.visitaMedicaRepository.findVisitaMedicaByPaziente_nome(name);
        if (visitaMedicaFound.isEmpty()) {
            throw new EntityNotFoundException("Nessuna visita medica trovata per il paziente con nome: " + name);
        }
        return (VisitaMedica) visitaMedicaFound;
    }

    public VisitaMedica findByMedico(String name) {
        List<VisitaMedica> visitaMedicaFound = this.visitaMedicaRepository.findVisitaMedicaByMedico_nome(name);
        if (visitaMedicaFound.isEmpty()) {
            throw new EntityNotFoundException("Nessuna visita medica trovata per il paziente con nome: " + name);
        }
        return (VisitaMedica) visitaMedicaFound;
    }

    public VisitaMedica findByNote(String note) {
        List<VisitaMedica> visitaMedicaFound = this.visitaMedicaRepository.findVisitaMedicaByNote(note);
        if (visitaMedicaFound.isEmpty()) {
            throw new EntityNotFoundException("Nessuna visita medica trovata per il paziente con nome: " + note);
        }
        return (VisitaMedica) visitaMedicaFound;
    }

    public void findAndDelete(UUID idVisitaMedica) {
        VisitaMedica visitaMedicaFound = findVisitaMedicaById(idVisitaMedica);
        this.visitaMedicaRepository.delete(visitaMedicaFound);
    }

    public List<VisitaMedica> trovaEventiPerVisita(String visitaPrenotabile) {
        List<VisitaMedica> visiteMedicheTrovate = visitaMedicaRepository.findAllByVisitaPrenotabile_Tipo(visitaPrenotabile);
        if (visiteMedicheTrovate.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la visita: " + visitaPrenotabile);
        }
        return visiteMedicheTrovate;
    }

    public List<VisitaMedica> trovaEventiPerIdVisitaPrenotabile(UUID visitaPrenotabile) {
        List<VisitaMedica> visiteMedicheTrovate = visitaMedicaRepository.findAllByVisitaPrenotabile_idTipoVisita(visitaPrenotabile);
        if (visiteMedicheTrovate.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la visita: " + visitaPrenotabile);
        }
        return visiteMedicheTrovate;
    }

    public List<VisitaMedica> trovaTuttiGliEventiPerData(LocalDate data) {
        List<VisitaMedica> visiteMedicheTrovate = visitaMedicaRepository.findAllByDataInizio(data);
        if (visiteMedicheTrovate.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la data: " + data);
        }
        return visiteMedicheTrovate;
    }

    public List<VisitaMedica> trovaTuttiGliEventiPerOra(LocalTime ora) {
        List<VisitaMedica> visiteMedicheTrovate = visitaMedicaRepository.findAllByOraInizio(ora);
        if (visiteMedicheTrovate.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per l'ora: " + ora);
        }
        return visiteMedicheTrovate;
    }

    public List<VisitaMedica> trovaTuttiGliEventiPerDataEOra(LocalDate data, LocalTime ora) {
        List<VisitaMedica> visiteMedicheTrovate = visitaMedicaRepository.findAllByDataInizioAndOraInizio(data, ora);
        if (visiteMedicheTrovate.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la data: " + data + " e l'ora: " + ora);
        }
        return visiteMedicheTrovate;
    }

    public List<VisitaMedica> trovaTuttiGliEventiPerNomeEData(String nome, LocalDate data) {
        List<VisitaMedica> visiteMedicheTrovate = visitaMedicaRepository.findAllByNomeAndDataInizio(nome, data);
        if (visiteMedicheTrovate.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la data: " + nome + " e l'ora: " + data);
        }
        return visiteMedicheTrovate;
    }

}
