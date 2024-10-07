package PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.AnalisiMediche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnalisiMedicheRepository extends JpaRepository<AnalisiMediche, UUID> {
}
