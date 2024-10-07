package PetrovTodor.PepeMedicalKids.repositorys.fatturazione;

import PetrovTodor.PepeMedicalKids.entities.fatturazione.MastrinoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MastrinoClienteRepository extends JpaRepository<MastrinoCliente, UUID> {
}
