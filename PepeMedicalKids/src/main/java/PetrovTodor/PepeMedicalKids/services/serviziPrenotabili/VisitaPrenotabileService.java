package PetrovTodor.PepeMedicalKids.services.serviziPrenotabili;

import PetrovTodor.PepeMedicalKids.repositorys.serviziPrenotabili.VisitaPrenotabileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitaPrenotabileService {
    @Autowired
    private VisitaPrenotabileRepository visitaPrenotabileRepository;
}
