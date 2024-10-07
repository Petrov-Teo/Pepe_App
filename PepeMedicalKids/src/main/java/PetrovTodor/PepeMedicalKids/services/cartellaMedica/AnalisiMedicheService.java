package PetrovTodor.PepeMedicalKids.services.cartellaMedica;

import PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica.AnalisiMedicheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalisiMedicheService {
    @Autowired
    private AnalisiMedicheRepository analisiMedicheRepository;

}
