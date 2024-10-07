package PetrovTodor.PepeMedicalKids.services.magazzino;

import PetrovTodor.PepeMedicalKids.repositorys.magazzino.CaricoMagazzinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaricoMagazzinoService {
    @Autowired
    private CaricoMagazzinoRepository caricoMagazzinoRepository;
}
