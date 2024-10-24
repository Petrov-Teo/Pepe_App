package PetrovTodor.PepeMedicalKids.repositorys.users;

import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PazienteRepository extends JpaRepository<Paziente, UUID> {
    @Query("SELECT MAX(a.codPaziente) FROM Paziente a")
    String findMaxPaziente();

    Optional<Paziente> findPazienteByCodPaziente(String codPaziente);

    List<Paziente> findPazienteByNome(String nome);

    List<Paziente> findPazienteByCognome(String cognome);

    Optional<Paziente> findByCodiceFiscale(String codiceFiscale);


    Optional<Paziente> findByEmail(String email);
}
