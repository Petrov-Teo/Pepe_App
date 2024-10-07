package PetrovTodor.PepeMedicalKids.services.manutenzioni;

import PetrovTodor.PepeMedicalKids.repositorys.manutenzioni.ContrattoManutenzioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContrattoManutenzioneService {
    @Autowired
    private ContrattoManutenzioniRepository contrattoManutenzioniRepository;
}
