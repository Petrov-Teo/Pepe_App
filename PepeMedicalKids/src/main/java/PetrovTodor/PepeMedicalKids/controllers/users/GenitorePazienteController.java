package PetrovTodor.PepeMedicalKids.controllers.users;

import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.payload.user.GenitoreTutoreDTO;
import PetrovTodor.PepeMedicalKids.services.users.GenitoreTutoreService;
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
@RequestMapping("/genitori")
public class GenitorePazienteController {

    @Autowired
    private GenitoreTutoreService genitoreTutoreService;

    // FIND ALL

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','RECEPTIONIST')")
    public Page<GenitoreTutore> findAll(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "codGenitore") String sorteBy) {
        return genitoreTutoreService.findAll(page, size, sorteBy);
    }

    // FIND ME
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('GENITORE')")
    public GenitoreTutore findMe(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("L'utente non è autenticato!");
        }
        String username = userDetails.getUsername();
        GenitoreTutore genitore = genitoreTutoreService.findByEmail(username);
        return genitore;
    }

    //FIND BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST','ADMIN')")
    public GenitoreTutore getGenitoreById(@PathVariable UUID id) throws MessagingException {
        return genitoreTutoreService.findById(id);
    }

    //UPDATE PAZIENTE
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST','ADMIN','GENITORE')")
    public GenitoreTutore updateGenitore(@PathVariable UUID id, @RequestBody GenitoreTutoreDTO payload) throws MessagingException {
        return this.genitoreTutoreService.findAndUpdate(id, payload);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST','ADMIN','GENITORE')")
    public void deleteGenitore(@PathVariable UUID id) throws MessagingException {
        this.genitoreTutoreService.findAndDelete(id);
    }
}
