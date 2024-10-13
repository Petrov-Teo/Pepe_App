package PetrovTodor.PepeMedicalKids.services.magazzino;

import PetrovTodor.PepeMedicalKids.repositorys.magazzino.ArticoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticoloService {
    @Autowired
    private ArticoloRepository articoloRepository;
}
