package PetrovTodor.PepeMedicalKids.services.manutenzioni;

import PetrovTodor.PepeMedicalKids.repositorys.manutenzioni.MacchinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MacchinarioService {
    @Autowired
    private MacchinarioRepository macchinarioRepository;
    
}
