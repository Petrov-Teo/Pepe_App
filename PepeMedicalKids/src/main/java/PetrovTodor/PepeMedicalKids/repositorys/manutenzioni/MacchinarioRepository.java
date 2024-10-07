package PetrovTodor.PepeMedicalKids.repositorys.manutenzioni;

import PetrovTodor.PepeMedicalKids.entities.manutenzioni.Macchinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MacchinarioRepository extends JpaRepository<Macchinario, UUID> {
}
