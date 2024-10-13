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


    Optional<Medico> findMedicoByNome(String nome);

    Optional<Medico> findMedicoByCognome(String cognome);

    Optional<Medico> findMedicoByCodMedico(String codMedico);

    Optional<Medico> findMedicoByCodiceFiscale(String codFiscale);
}
