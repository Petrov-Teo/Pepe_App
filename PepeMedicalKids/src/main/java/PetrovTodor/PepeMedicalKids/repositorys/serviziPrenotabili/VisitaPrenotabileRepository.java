package PetrovTodor.PepeMedicalKids.repositorys.serviziPrenotabili;

import PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili.VisitaPrenotabile;
import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VisitaPrenotabileRepository extends JpaRepository<VisitaPrenotabile, UUID> {

    Optional<VisitaPrenotabile> findByTipo(String tipo);

    Optional<VisitaPrenotabile> findByDescrizione(String descrizione);

    Optional<VisitaPrenotabile> findByMedico(Medico medico);
}
