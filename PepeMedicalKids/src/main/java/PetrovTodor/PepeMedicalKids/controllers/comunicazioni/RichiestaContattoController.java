package PetrovTodor.PepeMedicalKids.controllers.comunicazioni;

import PetrovTodor.PepeMedicalKids.entities.cominicazioni.RichiestaContatto;
import PetrovTodor.PepeMedicalKids.payload.comunicazioni.RichiestaContattoDTO;
import PetrovTodor.PepeMedicalKids.services.comunicazioni.RichiestaContattoService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comunicazioni")
public class RichiestaContattoController {
    @Autowired
    private RichiestaContattoService richiestaContattoService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','RECEPTIONIST')")
    public Page<RichiestaContatto> findAll(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "50") int size,
                                           @RequestParam(defaultValue = "nome") String sorteBy) {

        return richiestaContattoService.findAll(page, size, sorteBy);
    }

    @PostMapping("/salva")
    @ResponseStatus(HttpStatus.CREATED)
    public RichiestaContatto saveRichiestaContatto(@RequestBody RichiestaContattoDTO body) throws MessagingException {
        return this.richiestaContattoService.saveRichiestaContatto(body);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','RECEPTIONIST')")
    public RichiestaContatto findByIdRichiestaContatto(@PathVariable UUID id) {
        return this.richiestaContattoService.findByIdRichiestaContatto(id);
    }

    @PostMapping("/gestisci/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','RECEPTIONIST')")
    @ResponseStatus(HttpStatus.CREATED)
    public RichiestaContatto findAndManage(@PathVariable UUID id) {
        return this.richiestaContattoService.findAndGestisci(id);
    }

    @PostMapping("/archivia/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','RECEPTIONIST')")
    @ResponseStatus(HttpStatus.CREATED)
    public RichiestaContatto findAndArchivia(@PathVariable UUID id) {
        return this.richiestaContattoService.findAndArchivia(id);
    }

    @PostMapping("/rimetti-in-gestione/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','RECEPTIONIST')")
    @ResponseStatus(HttpStatus.CREATED)
    public RichiestaContatto findAndRimange(@PathVariable UUID id) {
        return this.richiestaContattoService.findAndRimanage(id);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','RECEPTIONIST')")
    @ResponseStatus(HttpStatus.CREATED)
    public RichiestaContatto findAndUpdateRichiestaContatto(@PathVariable UUID id, @RequestBody RichiestaContattoDTO body) {
        return this.richiestaContattoService.findAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN','RECEPTIONIST')")
    public void findAndDeleteRichiestaContatto(@PathVariable UUID id) {
        this.richiestaContattoService.findAndDelete(id);
    }

}
