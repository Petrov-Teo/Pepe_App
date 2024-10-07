package PetrovTodor.PepeMedicalKids.services.calendario;

import PetrovTodor.PepeMedicalKids.repositorys.calendario.VisitaMedicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitaMedicaService {
    @Autowired
    private VisitaMedicaRepository visitaMedicaRepository;
}
