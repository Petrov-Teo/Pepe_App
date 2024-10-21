package PetrovTodor.PepeMedicalKids.controllers.calendario;

import PetrovTodor.PepeMedicalKids.entities.calendario.EventoGenerico;
import PetrovTodor.PepeMedicalKids.payload.calendar.EventoGenericoDTO;
import PetrovTodor.PepeMedicalKids.services.calendario.EventoGenericoService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/genericEvents")
public class CalendarioController {

    @Autowired
    private EventoGenericoService eventoGenericoService;

    //GET ALL
    @GetMapping
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST','ADMIN')")
    public Page<EventoGenerico> findAll(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "idEvento") String sorteBy) {

        return eventoGenericoService.findAll(page, size, sorteBy);
    }

    //POST NEW
    @PostMapping("/nuovoEventoGenerico")
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST','ADMIN')")
    public List<EventoGenerico> nuovoEvento(@RequestBody EventoGenericoDTO body, BindingResult validationResult,
                                            @AuthenticationPrincipal UserDetails userDetails) throws MessagingException {
        return (List<EventoGenerico>) this.eventoGenericoService.saveEventoGenerico(body);
    }


    //POST UPDITE
    @PostMapping("/update-evento/{id}")
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST','ADMIN')")
    public EventoGenerico updateEventoGenerico(@PathVariable UUID id, @RequestBody EventoGenericoDTO payload) throws MessagingException {
        return this.eventoGenericoService.modificaEventoGenerico(id, payload);
    }

    //FINDS
    @GetMapping("{id}")
    public EventoGenerico getEventoById(@PathVariable UUID id) throws MessagingException {
        return this.eventoGenericoService.findById(id);
    }

    @GetMapping("/nome/{nomeEvento}")
    public String findByName(@PathVariable String nomeEvento) throws MessagingException {
        return this.eventoGenericoService.findByNomeEvento(nomeEvento);
    }

}



