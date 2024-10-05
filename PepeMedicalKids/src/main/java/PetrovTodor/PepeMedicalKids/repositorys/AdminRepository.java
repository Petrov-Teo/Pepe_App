package PetrovTodor.PepeMedicalKids.repositorys;


import PetrovTodor.PepeMedicalKids.entities.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    @Query("SELECT MAX(a.codAdmin) FROM Admin a")
    String findMaxCodAdmin();
}
