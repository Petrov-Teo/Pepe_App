package PetrovTodor.PepeMedicalKids.repositorys.magazzino;

import PetrovTodor.PepeMedicalKids.entities.magazino.MovimentazioneMagazzino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovimentazioneMagazzinoRepository extends JpaRepository<MovimentazioneMagazzino, UUID> {
}
