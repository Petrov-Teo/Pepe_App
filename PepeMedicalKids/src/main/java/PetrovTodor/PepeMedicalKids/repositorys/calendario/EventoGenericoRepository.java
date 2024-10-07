package PetrovTodor.PepeMedicalKids.repositorys.calendario;

import PetrovTodor.PepeMedicalKids.entities.calendario.EventoGenerico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventoGenericoRepository extends JpaRepository<EventoGenerico, UUID> {
}
