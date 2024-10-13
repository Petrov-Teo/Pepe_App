package PetrovTodor.PepeMedicalKids.services.comunicazioni;

import PetrovTodor.PepeMedicalKids.repositorys.comunicazioni.MessaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessaggiService {
    @Autowired
    private MessaggiRepository messaggiRepository;
}
