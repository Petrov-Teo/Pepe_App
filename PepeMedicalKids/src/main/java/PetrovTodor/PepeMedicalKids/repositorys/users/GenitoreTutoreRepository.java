package PetrovTodor.PepeMedicalKids.repositorys.users;

import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface GenitoreTutoreRepository extends JpaRepository<GenitoreTutore, UUID> {
    @Query("SELECT MAX(a.codGenitore) FROM GenitoreTutore a")
    String findMaxCodGenitore();


    Optional<GenitoreTutore> findByCodGenitore(String codGenitore);

    Optional<GenitoreTutore> findByNome(String nome);

    Optional<GenitoreTutore> findByCognome(String cognome);

    Optional<GenitoreTutore> findByCodiceFiscale(String codiceFiscale);

    Optional<GenitoreTutore> findByEmail(String email);

}
