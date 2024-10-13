package PetrovTodor.PepeMedicalKids.repositorys.users;

import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenitoreTutoreRepository extends JpaRepository<GenitoreTutore, UUID> {

    GenitoreTutore findGenitoreTutoreByCodGenitore(String codGenitore);
}
