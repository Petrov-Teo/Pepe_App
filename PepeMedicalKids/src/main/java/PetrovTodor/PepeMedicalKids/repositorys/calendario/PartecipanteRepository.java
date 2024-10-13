package PetrovTodor.PepeMedicalKids.repositorys.calendario;

import PetrovTodor.PepeMedicalKids.entities.calendario.Partecipante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PartecipanteRepository extends JpaRepository<Partecipante, UUID> {

    @Query
    Partecipante findByEmail(String email);


}
