package PetrovTodor.PepeMedicalKids.services.finanza;

import PetrovTodor.PepeMedicalKids.repositorys.finanza.BancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BancaService {
    @Autowired
    private BancaRepository bancaRepository;
}
