package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.repositorys.users.GenitoreTutoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenitoreTutoreService {
    @Autowired
    private GenitoreTutoreRepository genitoreTutoreRepository;


    public GenitoreTutore findByCodGenitore(String codGenitore) {
        return this.genitoreTutoreRepository.findGenitoreTutoreByCodGenitore(codGenitore);
    }

}
