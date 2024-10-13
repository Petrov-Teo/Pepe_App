package PetrovTodor.PepeMedicalKids.services.fatturazione;

import PetrovTodor.PepeMedicalKids.repositorys.fatturazione.MastrinoFornitoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MastrinoFornitoreService {
    @Autowired
    private MastrinoFornitoreRepository mastrinoFornitoreRepository;
}
