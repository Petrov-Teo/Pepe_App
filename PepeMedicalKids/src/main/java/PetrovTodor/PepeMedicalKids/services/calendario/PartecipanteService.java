package PetrovTodor.PepeMedicalKids.services.calendario;

import PetrovTodor.PepeMedicalKids.entities.calendario.Partecipante;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.repositorys.calendario.PartecipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PartecipanteService {
    @Autowired
    private PartecipanteRepository partecipanteRepository;

    //CRUD

    public Partecipante findById(UUID idPartecipante) {
        return partecipanteRepository.findById(idPartecipante).
                orElseThrow(() -> new NotFoundException("Partecipante con ID " + idPartecipante + " non trovato."));

    }

    public Partecipante save(Partecipante body) {
        Optional<Partecipante> partecipanteOpt = Optional.ofNullable(partecipanteRepository.findByEmail(body.getEmail()));
        if (partecipanteOpt.isPresent()) {
            throw new NotFoundException("Email già utilizzata: " + body.getEmail());
        }
        return partecipanteRepository.save(body);
    }

    public Partecipante findAndUpdate(UUID idPartecipante, Partecipante body) {
        Optional<Partecipante> partecipanteTrovato = partecipanteRepository.findById(idPartecipante);
        if (partecipanteTrovato.isPresent()) {
            Partecipante partecipante = partecipanteTrovato.get();
            partecipante.setNome(body.getNome());
            partecipante.setTipo(body.getTipo());
            partecipante.setEmail(body.getEmail());
            return partecipante;
        } else {
            throw new NotFoundException("Partecipante non trovato");
        }
    }

    public void findAndDelete(UUID idPartecipante) {
        Optional<Partecipante> partecipanteTrovato = partecipanteRepository.findById(idPartecipante);
        if (partecipanteTrovato.isPresent()) {
            Partecipante partecipante = partecipanteTrovato.get();
            this.partecipanteRepository.delete(partecipante);
        } else {
            throw new NotFoundException("Partecipante non trovato");
        }
    }

}


