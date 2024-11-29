package PetrovTodor.PepeMedicalKids.controllers.users;

import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import PetrovTodor.PepeMedicalKids.exceptions.UnauthorizedException;
import PetrovTodor.PepeMedicalKids.payload.user.PazienteMaggiorenneDTO;
import PetrovTodor.PepeMedicalKids.services.users.PazienteService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pazienti")
public class PazienteMaggiorenneController {

    @Autowired
    private PazienteService pazienteService;


    //FIND ALL
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN','RECEPTIONIST','MEDICO')")
    public Page<Paziente> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "codPaziente") String sorteBy) {
        return pazienteService.findAll(page, size, sorteBy);
    }

//FIND ME

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('PAZIENTE')")
    public Paziente findMe(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new UnauthorizedException("L'utente non è autenticato!");
        }
        String username = userDetails.getUsername();
        Paziente paziente = pazienteService.findByEmail(username);
        return paziente;
    }

    //FIND BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','RECEPTIONIST')")
    public Paziente GetPazienteById(@PathVariable UUID id) throws MessagingException {
        return pazienteService.findPazienteByID(id);
    }

    // UPDITE PAZIENTE
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST','ADMIN','PAZIENTE')")
    public Paziente updatePaziente(@PathVariable UUID id, @RequestBody PazienteMaggiorenneDTO payload) throws MessagingException {
        return this.pazienteService.findAndUpdateMaggiorenne(id, payload);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST','ADMIN','PAZIENTE')")
    public void deletePaziente(@PathVariable UUID id) throws MessagingException {
        this.pazienteService.findAndDelete(id);
    }
}
