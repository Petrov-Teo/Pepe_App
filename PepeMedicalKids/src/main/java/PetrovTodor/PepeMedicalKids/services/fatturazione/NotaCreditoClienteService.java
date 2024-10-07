package PetrovTodor.PepeMedicalKids.services.fatturazione;

import PetrovTodor.PepeMedicalKids.repositorys.fatturazione.NotaCreditoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotaCreditoClienteService {
    @Autowired
    private NotaCreditoClienteRepository notaCreditoClienteRepository;
}
