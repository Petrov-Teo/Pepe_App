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
      return  partecipanteRepository.findById(idPartecipante).
                orElseThrow(()->new NotFoundException("Partecipante con ID " + idPartecipante + " non trovato."));

    }

    public Partecipante save(Partecipante body) {
        Optional<Partecipante> partecipanteOpt = Optional.ofNullable(partecipanteRepository.findByEmail(body.getEmail()));
        if (partecipanteOpt.isPresent()) {
            throw new NotFoundException("Email già utilizzata: " + body.getEmail());
        }
        return partecipanteRepository.save(body);
    }

    public Partecipante findAndUpdite(UUID idPartecipante, Partecipante body){
        
        return
    }

}


