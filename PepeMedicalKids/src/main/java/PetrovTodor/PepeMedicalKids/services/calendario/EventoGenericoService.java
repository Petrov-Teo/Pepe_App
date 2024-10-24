package PetrovTodor.PepeMedicalKids.services.calendario;

import PetrovTodor.PepeMedicalKids.entities.calendario.EventoGenerico;
import PetrovTodor.PepeMedicalKids.enums.TipoRicorrenza;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.calendar.EventoGenericoDTO;
import PetrovTodor.PepeMedicalKids.repositorys.calendario.EventoGenericoRepository;
import PetrovTodor.PepeMedicalKids.services.emeil.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventoGenericoService {
    @Autowired
    private EventoGenericoRepository eventoGenericoRepository;
    @Autowired
    private EmailService emailService;

    //CRUD

    public Page<EventoGenerico> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventoGenericoRepository.findAll(pageable);
    }

    public EventoGenerico findById(UUID idEvento) {
        return eventoGenericoRepository.findById(idEvento)
                .orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + idEvento));
    }

    public List<EventoGenerico> saveEventoGenerico(EventoGenericoDTO body) throws MessagingException {
        List<EventoGenerico> eventiGenerici = new ArrayList<>();

        if (!body.eventoRicorrente()) {
            EventoGenerico eventoGenerico = createEventoGenerico(body, body.dataInizio());
            eventiGenerici.add(eventoGenerico);
        } else {
            LocalDate dataInizio = body.dataInizio();
            LocalDate dataFine = body.dataFineRicorrenza();

            if (dataFine == null || dataFine.isBefore(dataInizio)) {
                throw new IllegalArgumentException("La data di fine deve essere successiva alla data di inizio.");
            }

            switch (body.tipoRicorrenza()) {
                case GIORNALIERA:
                    while (!dataInizio.isAfter(dataFine)) {
                        EventoGenerico eventoGenerico = createEventoGenerico(body, dataInizio);
                        eventiGenerici.add(eventoGenerico);
                        dataInizio = dataInizio.plusDays(1);
                    }
                    break;
                case SETTIMANALE:
                    while (!dataInizio.isAfter(dataFine)) {
                        EventoGenerico eventoGenerico = createEventoGenerico(body, dataInizio);
                        eventiGenerici.add(eventoGenerico);
                        dataInizio = dataInizio.plusWeeks(1);
                    }
                    break;
                case MENSILE:
                    while (!dataInizio.isAfter(dataFine)) {
                        EventoGenerico eventoGenerico = createEventoGenerico(body, dataInizio);
                        eventiGenerici.add(eventoGenerico);
                        dataInizio = dataInizio.plusMonths(1);
                    }
                    break;
                case ANNUALE:
                    while (!dataInizio.isAfter(dataFine)) {
                        EventoGenerico eventoGenerico = createEventoGenerico(body, dataInizio);
                        eventiGenerici.add(eventoGenerico);
                        dataInizio = dataInizio.plusYears(1);
                    }
                    break;
            }
        }
        for (String partecipante : body.partecipanti()) {
            emailService.sendEventNotification(partecipante, body);
        }

        return eventoGenericoRepository.saveAll(eventiGenerici);
    }

    private EventoGenerico createEventoGenerico(EventoGenericoDTO body, LocalDate dataInizio) {
        EventoGenerico eventoGenerico = new EventoGenerico(
                body.nome(),
                dataInizio,
                body.oraInizio(),
                body.oraFine(),
                body.note(),
                body.luogo(),
                body.partecipanti(),
                true,
                body.tipoRicorrenza(),
                body.dataFineRicorrenza());

        if (body.tipoRicorrenza() == null) {
            eventoGenerico.setTipoRicorrenza(TipoRicorrenza.NON_RICORRENTE);
        }

        if (body.dataFineRicorrenza() == null) {
            eventoGenerico.setDataFineRicorrenza(body.dataInizio());
        }
        return eventoGenerico;
    }

    public List<EventoGenerico> modificaEventoGenerico(UUID idEvento, EventoGenericoDTO body) throws MessagingException {
        EventoGenerico eventoDaModificare = findById(idEvento);
        List<EventoGenerico> eventiModificati = new ArrayList<>();

        if (!body.eventoRicorrente()) {
            // Se l'evento non è ricorrente, modifichiamo solo questo evento
            eventoDaModificare.setNome(body.nome());
            eventoDaModificare.setDataInizio(body.dataInizio());
            eventoDaModificare.setOraInizio(body.oraInizio());
            eventoDaModificare.setOraFine(body.oraFine());
            eventoDaModificare.setNote(body.note());
            eventoDaModificare.setLuogo(body.luogo());
            eventoDaModificare.setPartecipanti(body.partecipanti());
            eventoDaModificare.setEventoRicorrente(false);
            eventoDaModificare.setTipoRicorrenza(TipoRicorrenza.NON_RICORRENTE);
            eventoDaModificare.setDataFineRicorrenza(null);

            eventiModificati.add(eventoGenericoRepository.save(eventoDaModificare));
        } else {
            // Se l'evento è ricorrente, eliminiamo le occorrenze future e creiamo le nuove
            List<EventoGenerico> occorrenzeFuture = eventoGenericoRepository.findFutureOccurrences(idEvento);

            // Elimina le occorrenze future dell'evento se presenti
            eventoGenericoRepository.deleteAll(occorrenzeFuture);

            // Crea e salva le nuove occorrenze in base alla nuova configurazione di ricorrenza
            LocalDate dataInizio = body.dataInizio();
            LocalDate dataFine = body.dataFineRicorrenza();

            if (dataFine == null || dataFine.isBefore(dataInizio)) {
                throw new IllegalArgumentException("La data di fine deve essere successiva alla data di inizio.");
            }

            switch (body.tipoRicorrenza()) {
                case GIORNALIERA:
                    while (!dataInizio.isAfter(dataFine)) {
                        EventoGenerico eventoGenerico = createEventoGenerico(body, dataInizio);
                        eventiModificati.add(eventoGenerico);
                        dataInizio = dataInizio.plusDays(1);
                    }
                    break;
                case SETTIMANALE:
                    while (!dataInizio.isAfter(dataFine)) {
                        EventoGenerico eventoGenerico = createEventoGenerico(body, dataInizio);
                        eventiModificati.add(eventoGenerico);
                        dataInizio = dataInizio.plusWeeks(1);
                    }
                    break;
                case MENSILE:
                    while (!dataInizio.isAfter(dataFine)) {
                        EventoGenerico eventoGenerico = createEventoGenerico(body, dataInizio);
                        eventiModificati.add(eventoGenerico);
                        dataInizio = dataInizio.plusMonths(1);
                    }
                    break;
                case ANNUALE:
                    while (!dataInizio.isAfter(dataFine)) {
                        EventoGenerico eventoGenerico = createEventoGenerico(body, dataInizio);
                        eventiModificati.add(eventoGenerico);
                        dataInizio = dataInizio.plusYears(1);
                    }
                    break;
            }

            this.eventoGenericoRepository.saveAll(eventiModificati);
        }

        // Invia una notifica di modifica dell'evento ai partecipanti
        for (String partecipante : body.partecipanti()) {
            this.emailService.sendEventNotification(partecipante, body);
        }

        return eventiModificati;
    }

    public void findAndDelete(UUID idEvento, boolean deleteEntireSeries) {
        EventoGenerico eventoDaCancellare = findById(idEvento);

        if (deleteEntireSeries) {
            // Cancella tutti gli eventi ricorrenti e l'evento specificato
            List<EventoGenerico> eventiRicorrenti = eventoGenericoRepository.findAllByNomeAndDataInizioAfter(
                    eventoDaCancellare.getNome(),
                    eventoDaCancellare.getDataInizio()
            );

            // Aggiungi l'evento corrente per assicurarti che venga cancellato
            eventiRicorrenti.add(eventoDaCancellare);

            eventoGenericoRepository.deleteAll(eventiRicorrenti);
        } else {
            // Cancella solo l'evento specificato
            eventoGenericoRepository.delete(eventoDaCancellare);
        }
    }

    public String findByNomeEvento(String nomeEvento) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByNome(nomeEvento);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato con il nome: " + nomeEvento);
        }
        return eventi.toString();
    }

    public String findByNoteEvento(String note) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByNote(note);
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

    public List<EventoGenerico> trovaTuttiGliEventiPerNomeEData(String nome, LocalDate data) {
        List<EventoGenerico> eventi = eventoGenericoRepository.findAllByNomeAndDataInizio(nome, data);
        if (eventi.isEmpty()) {
            throw new NotFoundException("Nessun evento trovato per la data: " + nome + " e l'ora: " + data);
        }
        return eventi;
    }

}