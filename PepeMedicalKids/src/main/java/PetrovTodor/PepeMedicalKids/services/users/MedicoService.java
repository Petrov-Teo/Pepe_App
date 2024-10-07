package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.repositorys.users.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

}
