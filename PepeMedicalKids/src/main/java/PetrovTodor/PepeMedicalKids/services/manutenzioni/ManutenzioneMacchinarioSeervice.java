package PetrovTodor.PepeMedicalKids.services.manutenzioni;

import PetrovTodor.PepeMedicalKids.repositorys.manutenzioni.ManutenzioneMacchinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManutenzioneMacchinarioSeervice {
    @Autowired
    private ManutenzioneMacchinarioRepository manutenzioneMacchinarioRepository;
}
