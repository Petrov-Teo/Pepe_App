package PetrovTodor.PepeMedicalKids.services.finanza;

import PetrovTodor.PepeMedicalKids.repositorys.finanza.CassaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CassaService {
    @Autowired
    private CassaRepository cassaRepository;
}
