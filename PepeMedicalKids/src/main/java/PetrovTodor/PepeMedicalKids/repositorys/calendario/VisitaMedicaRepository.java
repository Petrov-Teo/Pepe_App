package PetrovTodor.PepeMedicalKids.repositorys.calendario;

import PetrovTodor.PepeMedicalKids.entities.calendario.VisitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface VisitaMedicaRepository extends JpaRepository<VisitaMedica, UUID> {
    List<VisitaMedica> findVisitaMedicaByPaziente_nome(String nome);

    List<VisitaMedica> findVisitaMedicaByMedico_nome(String nome);

    List<VisitaMedica> findVisitaMedicaByNote(String nome);

    List<VisitaMedica> findAllByVisitaPrenotabile_idTipoVisita(UUID idTipovisita);

    List<VisitaMedica> findAllByVisitaPrenotabile_Tipo(String tipo);

    List<VisitaMedica> findAllByDataInizio(LocalDate data);


    List<VisitaMedica> findAllByOraInizio(LocalTime ora);


    List<VisitaMedica> findAllByNomeAndDataInizio(String nome, LocalDate data);


    List<VisitaMedica> findAllByDataInizioAndOraInizio(LocalDate data, LocalTime oraInizio);

}
