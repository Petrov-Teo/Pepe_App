package PetrovTodor.PepeMedicalKids.services.cartellaMedica;

import PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica.AnalisiRegionaliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalisiRegionaliService {
    @Autowired
    private AnalisiRegionaliRepository analisiRegionaliRepository;
}
