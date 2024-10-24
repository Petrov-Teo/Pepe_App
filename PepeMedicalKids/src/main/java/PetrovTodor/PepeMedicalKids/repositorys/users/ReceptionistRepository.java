package PetrovTodor.PepeMedicalKids.repositorys.users;

import PetrovTodor.PepeMedicalKids.entities.users.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReceptionistRepository extends JpaRepository<Receptionist, UUID> {
    @Query("SELECT MAX(a.codReceptionist) FROM Receptionist a")
    String findMaxCodReceptionist();

    Optional<Receptionist> findByCodReceptionist(String codReceptionist);

    Optional<Receptionist> findByNome(String nome);

    Optional<Receptionist> findByCognome(String cognome);

    Optional<Receptionist> findByCodiceFiscale(String codiceFiscale);

    Optional<Receptionist> findByEmail(String email);
}
