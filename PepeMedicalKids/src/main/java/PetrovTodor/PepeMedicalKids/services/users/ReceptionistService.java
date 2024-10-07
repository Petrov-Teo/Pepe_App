package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.repositorys.users.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceptionistService {
    @Autowired
    private ReceptionistRepository receptionistRepository;
}
