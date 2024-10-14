package PetrovTodor.PepeMedicalKids.services.serviziPrenotabili;

import PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili.VisitaPrenotabile;
import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.serviziPrenotabili.VisitaPrenotabileDTO;
import PetrovTodor.PepeMedicalKids.repositorys.serviziPrenotabili.VisitaPrenotabileRepository;
import PetrovTodor.PepeMedicalKids.services.users.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VisitaPrenotabileService {
    @Autowired
    private VisitaPrenotabileRepository visitaPrenotabileRepository;

    @Autowired
    private MedicoService medicoService;

    //FIND ALL
    public Page<VisitaPrenotabile> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.visitaPrenotabileRepository.findAll(pageable);
    }

    //SAVE VISISTA PRENOTABILE
    public VisitaPrenotabile saveVisitaPrenotabile(VisitaPrenotabileDTO body) {
        Medico foundMedico = medicoService.findMedicoByCodMedico(body.medico());
        VisitaPrenotabile visitaPrenotabile = new VisitaPrenotabile(
                body.tipo(),
                body.descrizione(),
                foundMedico,
                body.prezzo());

        return this.visitaPrenotabileRepository.save(visitaPrenotabile);
    }

    //FIND BY ID VISITAPRENOTBILE
    public VisitaPrenotabile findByIdVisitaPrenotabile(UUID idVisitaPrenotabile) {
        return this.visitaPrenotabileRepository.findById(idVisitaPrenotabile)
                .orElseThrow(()
                        -> new NotFoundException("Nessuna Visita Prenotabile trovata con il seguente id :" + idVisitaPrenotabile + "!"));
    }

    //FIND BY TIPO
    public VisitaPrenotabile findByTipoVisita(String tipo) {
        return this.visitaPrenotabileRepository.findByTipo(tipo)
                .orElseThrow(()
                        -> new NotFoundException("Nessuna Visita Prenotabile trovata con il seguente tipo :" + tipo + "!"));
    }

    //FIND BY DESCRIZIONE
    public VisitaPrenotabile findByDescrizione(String descrizione) {
        return this.visitaPrenotabileRepository.findByDescrizione(descrizione)
                .orElseThrow(()
                        -> new NotFoundException("Nessuna Visita Prenotabile trovata con il seguente Descrizione :" + descrizione + "!"));
    }

    //FIND BY MEDICO
    public VisitaPrenotabile findByDescrizione(Medico medico) {
        return this.visitaPrenotabileRepository.findByMedico(medico)
                .orElseThrow(()
                        -> new NotFoundException("Nessuna Visita Prenotabile trovata con il seguente Medico :" + medico + "!"));
    }

    //FIND AND UPDATE
    public VisitaPrenotabile findAndUpdate(UUID idVisitaPrenotabile, VisitaPrenotabileDTO body) {
        Medico foundMedico = medicoService.findMedicoByCodMedico(body.medico());
        VisitaPrenotabile found = findByIdVisitaPrenotabile(idVisitaPrenotabile);
        found.setTipo(body.tipo());
        found.setDescrizione(body.descrizione());
        found.setMedico(foundMedico);
        found.setPrezzo(body.prezzo());
        return this.visitaPrenotabileRepository.save(found);
    }

    //FIND AND DALETE

    public void findAndDelete(UUID idVisitaPrenotabile) {
        VisitaPrenotabile found = findByIdVisitaPrenotabile(idVisitaPrenotabile);
        this.visitaPrenotabileRepository.delete(found);
    }

}
