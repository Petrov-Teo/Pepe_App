package PetrovTodor.PepeMedicalKids.repositorys.calendario;

import PetrovTodor.PepeMedicalKids.entities.calendario.VisitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VisitaMedicaRepository extends JpaRepository<VisitaMedica, UUID> {
}
