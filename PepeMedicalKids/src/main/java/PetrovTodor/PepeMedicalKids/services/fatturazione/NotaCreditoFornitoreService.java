package PetrovTodor.PepeMedicalKids.services.fatturazione;

import PetrovTodor.PepeMedicalKids.repositorys.fatturazione.NotaCreditoFornitoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotaCreditoFornitoreService {
    @Autowired
    private NotaCreditoFornitoreRepository notaCreditoFornitoreRepository;
}
