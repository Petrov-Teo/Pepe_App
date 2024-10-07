package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.repositorys.users.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PazienteService {
    @Autowired
    private PazienteRepository pazienteRepository;
}
