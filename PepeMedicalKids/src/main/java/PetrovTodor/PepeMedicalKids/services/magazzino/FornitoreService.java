package PetrovTodor.PepeMedicalKids.services.magazzino;

import PetrovTodor.PepeMedicalKids.repositorys.magazzino.FornitoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FornitoreService {
    @Autowired
    private FornitoreRepository fornitoreRepository;
}
