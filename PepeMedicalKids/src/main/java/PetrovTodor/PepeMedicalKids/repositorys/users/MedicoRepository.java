package PetrovTodor.PepeMedicalKids.repositorys.users;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {
    @Query("SELECT MAX(a.codMedico) FROM Medico a")
    String findMaxCodMedico();


    Optional<Medico> findByNome(String nome);

    Optional<Medico> findByCognome(String cognome);

    Optional<Medico> findByCodMedico(String codMedico);

    Optional<Medico> findByCodiceFiscale(String codFiscale);

    Optional<Medico> findByEmail(String email);
}
