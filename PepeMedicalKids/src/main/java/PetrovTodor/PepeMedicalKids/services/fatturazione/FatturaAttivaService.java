package PetrovTodor.PepeMedicalKids.services.fatturazione;

import PetrovTodor.PepeMedicalKids.repositorys.fatturazione.FatturaAttivaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FatturaAttivaService {
    @Autowired
    private FatturaAttivaRepository fatturaAttivaRepository;
}
