package PetrovTodor.PepeMedicalKids.repositorys.magazzino;

import PetrovTodor.PepeMedicalKids.entities.magazino.Fornitore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FornitoreRepository extends JpaRepository<Fornitore, UUID> {
}
