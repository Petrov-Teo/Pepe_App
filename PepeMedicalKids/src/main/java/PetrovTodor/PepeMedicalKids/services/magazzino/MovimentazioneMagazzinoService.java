package PetrovTodor.PepeMedicalKids.services.magazzino;

import PetrovTodor.PepeMedicalKids.repositorys.magazzino.MovimentazioneMagazzinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentazioneMagazzinoService {
    @Autowired
    private MovimentazioneMagazzinoRepository movimentazioneMagazzinoRepository;
}
