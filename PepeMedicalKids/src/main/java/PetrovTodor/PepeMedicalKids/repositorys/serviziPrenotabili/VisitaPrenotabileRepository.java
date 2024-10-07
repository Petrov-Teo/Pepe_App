package PetrovTodor.PepeMedicalKids.repositorys.serviziPrenotabili;

import PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili.VisitaPrenotabile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VisitaPrenotabileRepository extends JpaRepository<VisitaPrenotabile, UUID> {
}
