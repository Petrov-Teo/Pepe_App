package PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.RefertoMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefertoMedicoRepository extends JpaRepository<RefertoMedico, UUID> {
    @Query("SELECT MAX(a.codRefertoMedico) FROM RefertoMedico a")
    String findMaxCodRefertoMedico();

    Optional<RefertoMedico> findByCodRefertoMedico(String codRefertoMedico);
}
