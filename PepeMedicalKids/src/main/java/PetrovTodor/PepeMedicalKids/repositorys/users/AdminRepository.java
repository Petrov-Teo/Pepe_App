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

    Optional<Admin> findAdminByCodAdmin(String codAdmin);

    Optional<Admin> findAdminByNome(String nome);

    Optional<Admin> findAdminByCognome(String cognome);

    Optional<Admin> findAdminByCodiceFiscale(String codiceFiscale);
}
