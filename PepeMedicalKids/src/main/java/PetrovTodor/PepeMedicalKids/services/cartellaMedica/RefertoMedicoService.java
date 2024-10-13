package PetrovTodor.PepeMedicalKids.services.cartellaMedica;

import PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica.RefertoMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefertoMedicoService {
    @Autowired
    private RefertoMedicoRepository refertoMedicoRepository;
}
