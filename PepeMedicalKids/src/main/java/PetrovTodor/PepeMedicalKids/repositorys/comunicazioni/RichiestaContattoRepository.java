package PetrovTodor.PepeMedicalKids.repositorys.comunicazioni;

import PetrovTodor.PepeMedicalKids.entities.cominicazioni.RichiestaContatto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RichiestaContattoRepository extends JpaRepository<RichiestaContatto, UUID> {

    Optional<RichiestaContatto> findByNome(String nome);

    Optional<RichiestaContatto> findByEmail(String email);
}
