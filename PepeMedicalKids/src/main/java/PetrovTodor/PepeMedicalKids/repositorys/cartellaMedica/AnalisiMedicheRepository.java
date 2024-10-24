package PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.AnalisiMediche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnalisiMedicheRepository extends JpaRepository<AnalisiMediche, UUID> {

    Optional<AnalisiMediche> findByCodAnalisi(String codAnalisi);

    @Query("SELECT MAX(codAnalisi) FROM AnalisiMediche a")
    String findMaxCodAnalisi();
}
