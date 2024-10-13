package PetrovTodor.PepeMedicalKids.repositorys.magazzino;

import PetrovTodor.PepeMedicalKids.entities.magazino.CaricoMagazzino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CaricoMagazzinoRepository extends JpaRepository<CaricoMagazzino, UUID> {
}
