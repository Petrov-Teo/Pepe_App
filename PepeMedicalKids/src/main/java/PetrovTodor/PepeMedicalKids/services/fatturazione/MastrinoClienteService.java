package PetrovTodor.PepeMedicalKids.services.fatturazione;

import PetrovTodor.PepeMedicalKids.repositorys.fatturazione.MastrinoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MastrinoClienteService {
    @Autowired
    private MastrinoClienteRepository mastrinoClienteRepository;
}
