package PetrovTodor.PepeMedicalKids.repositorys.calendario;

import PetrovTodor.PepeMedicalKids.entities.calendario.EventoGenerico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventoGenericoRepository extends JpaRepository<EventoGenerico, UUID> {
    Optional<EventoGenerico> findByNome(String nome);

    List<EventoGenerico> findAllByNome(String nome);

    List<EventoGenerico> findAllByDataInizio(LocalDate data);


    List<EventoGenerico> findAllByOraInizio(LocalTime ora);


    List<EventoGenerico> findAllByNomeAndDataInizio(String nome, LocalDate data);


    List<EventoGenerico> findAllByDataInizioAndOraInizio(LocalDate data, LocalTime oraInizio);

    List<EventoGenerico> findAllByNote(String note);

    List<EventoGenerico> findAllByNomeAndDataInizioAfter(String nome, LocalDate dataInizio);

    List<EventoGenerico> findByEventoRicorrente(boolean eventoRicorrente);

    List<EventoGenerico> findAllByNomeAndDataInizioBetween(String nome, LocalDate dataInizio, LocalDate dataFine);


//    List<EventoGenerico> findAllByVisitaPrenotabile_idTipoVisita(UUID idTipovisita);
//
//    List<EventoGenerico> findAllByVisitaPrenotabile_Tipo(String tipo);  TODO DA SPOSTARE NEL CALENDARIO VISITA MEDICA 
}
