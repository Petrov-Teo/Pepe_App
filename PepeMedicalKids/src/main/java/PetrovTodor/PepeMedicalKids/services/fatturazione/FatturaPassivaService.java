package PetrovTodor.PepeMedicalKids.services.fatturazione;

import PetrovTodor.PepeMedicalKids.repositorys.fatturazione.FatturaPassivaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FatturaPassivaService {
    @Autowired
    private FatturaPassivaRepository fatturaPassivaRepository;
}
