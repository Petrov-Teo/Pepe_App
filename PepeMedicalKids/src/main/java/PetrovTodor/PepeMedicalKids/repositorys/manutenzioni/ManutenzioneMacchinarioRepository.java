package PetrovTodor.PepeMedicalKids.repositorys.manutenzioni;

import PetrovTodor.PepeMedicalKids.entities.manutenzioni.ManutenzioneMacchinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ManutenzioneMacchinarioRepository extends JpaRepository<ManutenzioneMacchinario, UUID> {
}
