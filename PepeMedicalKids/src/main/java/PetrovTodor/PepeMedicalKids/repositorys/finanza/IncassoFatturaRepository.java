package PetrovTodor.PepeMedicalKids.repositorys.finanza;

import PetrovTodor.PepeMedicalKids.entities.finanza.IncassoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IncassoFatturaRepository extends JpaRepository<IncassoFattura, UUID> {
}
