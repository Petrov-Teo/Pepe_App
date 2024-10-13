package PetrovTodor.PepeMedicalKids.repositorys.users;


import PetrovTodor.PepeMedicalKids.entities.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    @Query("SELECT MAX(a.codAdmin) FROM Admin a")
    String findMaxCodAdmin();

    Optional<Admin> findByCodAdmin(String codAdmin);

    Optional<Admin> findByNome(String nome);

    Optional<Admin> findByCognome(String cognome);

    Optional<Admin> findByCodiceFiscale(String codiceFiscale);

    Optional<Admin> findByEmail(String email);
}
