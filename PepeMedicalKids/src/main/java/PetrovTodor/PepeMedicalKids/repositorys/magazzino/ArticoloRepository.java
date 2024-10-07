package PetrovTodor.PepeMedicalKids.repositorys.magazzino;

import PetrovTodor.PepeMedicalKids.entities.magazino.Articolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticoloRepository extends JpaRepository<Articolo, UUID> {
}
