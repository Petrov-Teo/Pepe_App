package PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefertoMedicoRepository extends JpaRepository<RefertoMedicoRepository, UUID> {
}
