package PetrovTodor.PepeMedicalKids.services.finanza;

import PetrovTodor.PepeMedicalKids.repositorys.finanza.IncassoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncassoFatturaService {
    @Autowired
    private IncassoFatturaRepository incassoFatturaRepository;
}
