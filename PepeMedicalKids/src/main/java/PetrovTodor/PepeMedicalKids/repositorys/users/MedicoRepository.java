package PetrovTodor.PepeMedicalKids.repositorys.users;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {
}
