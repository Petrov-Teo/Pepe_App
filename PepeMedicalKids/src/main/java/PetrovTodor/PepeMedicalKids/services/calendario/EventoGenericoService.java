package PetrovTodor.PepeMedicalKids.services.calendario;

import PetrovTodor.PepeMedicalKids.entities.calendario.EventoGenerico;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.EventoGenericoDTO;
import PetrovTodor.PepeMedicalKids.repositorys.calendario.EventoGenericoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class EventoGenericoService {
    @Autowired
    private EventoGenericoRepository eventoGenericoRepository;


    //CRUD

    public EventoGenerico findById(UUID idEvento) {
        return eventoGenericoRepository.findById(idEvento)
                .orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + idEvento));
    }

    public EventoGenerico saveEventoGenerico(EventoGenericoDTO body) {
        EventoGenerico eventoGenerico = new
                EventoGenerico(body.nome(),
                body.dataInizio(),
                body.oraInizio(),
                body.oraFine(),
                body.note(),
                body.luogo(),
                body.visitaPrenotabile(),
                body.partecipanti(),
                body.eventoRiccorrente(),
                body.tipoRicorrenza(),
                body.dataFineRiccorenza());
        return eventoGenericoRepository.save(eventoGenerico);
    }

    public EventoGenerico modificaEventoGenerico(UUID idEvento, EventoGenericoDTO body) {
        EventoGenerico eventoDaModificare = findById(idEvento);
        eventoDaModificare.setNome(body.nome());
        eventoDaModificare.setDataInizio(body.dataInizio());
        eventoDaModificare.setOraInizio(body.oraInizio());
        eventoDaModificare.setOraFine(body.oraFine());
        eventoDaModificare.setNote(body.note());
        eventoDaModificare.setLuogo(body.luogo());
        eventoDaModificare.setVisitaPrenotabile(body.visitaPrenotabile());
        eventoDaModificare.setPartecipanti(body.partecipanti());
        eventoDaModificare.setEventoRicorrente(body.eventoRiccorrente());
        eventoDaModificare.setTipoRicorrenza(body.tipoRicorrenza());
        eventoDaModificare.setDataFineRicorrenza(body.dataFineRiccorenza());

        return eventoGenericoRepository.save(eventoDaModificare);

    }

    public void findAndDelete(UUID idEvento) {
        EventoGenerico eventoDaCancellare = findById(idEvento);
        eventoGenericoRepository.delete(eventoDaCancellare);

    }

    public String findBynomeEvento(String nomeEvento) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByNome(nomeEvento);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato con il nome: " + nomeEvento);
        }
        return eventi.toString();
    }

    public String findByNoteEvento(String note) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllBynote(note);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato con il nome: " + note);
        }
        return eventi.toString();
    }

    public List<EventoGenerico> trovaTuttiGliEventiPerNome(String nome) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByNome(nome);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato con il nome: " + nome);
        }
        return eventi;
    }

    public List<EventoGenerico> trovaTuttiGliEventiPerData(LocalDate data) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByDataInizio(data);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la data: " + data);
        }
        return eventi;
    }

    public List<EventoGenerico> trovaTuttiGliEventiPerOra(LocalTime ora) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByOraInizio(ora);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per l'ora: " + ora);
        }
        return eventi;
    }

    public List<EventoGenerico> trovaTuttiGliEventiPerDataEOra(LocalDate data, LocalTime ora) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByDataInizioAndOraInizio(data, ora);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la data: " + data + " e l'ora: " + ora);
        }
        return eventi;
    }

    public List<EventoGenerico> trovaEventiPerVisita(String visitaPrenotabile) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByVisitaPrenotabile_Tipo(visitaPrenotabile);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la visita: " + visitaPrenotabile);
        }
        return eventi;
    }

    public List<EventoGenerico> trovaEventiPerIdVisitaPrenotabile(UUID visitaPrenotabile) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByVisitaPrenotabile_idTipoVisita(visitaPrenotabile);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la visita: " + visitaPrenotabile);
        }
        return eventi;
    }

    public List<EventoGenerico> trovaTuttiGliEventiPerNomeEData(String nome, LocalDate data) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByNomeAndDataInizio(nome, data);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la data: " + nome + " e l'ora: " + data);
        }
        return eventi;
    }

}
