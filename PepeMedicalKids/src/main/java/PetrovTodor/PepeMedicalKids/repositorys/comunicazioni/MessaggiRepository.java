package PetrovTodor.PepeMedicalKids.repositorys.comunicazioni;

import PetrovTodor.PepeMedicalKids.entities.cominicazioni.Messaggi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessaggiRepository extends JpaRepository<Messaggi, UUID> {
}
