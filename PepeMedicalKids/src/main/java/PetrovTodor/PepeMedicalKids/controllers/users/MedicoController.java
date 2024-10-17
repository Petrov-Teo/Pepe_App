package PetrovTodor.PepeMedicalKids.controllers.users;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.exceptions.BadRequestException;
import PetrovTodor.PepeMedicalKids.payload.user.MedicoDTO;
import PetrovTodor.PepeMedicalKids.payload.user.MedicoUpdateDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetDTO;
import PetrovTodor.PepeMedicalKids.services.users.MedicoService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/medici")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    //FIND ALL
    @GetMapping
    @PreAuthorize("hasAnyAuthority(, 'ADMIN')")
    public Page<Medico> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "codAdmin") String sorteBy) {

        return medicoService.findAll(page, size, sorteBy);
    }

    // FIND ME
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority( 'MEDICO','RECEPTIONIST','ADMIN')")
    public Medico findMe(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("L'utente non è autenticato!");
        }
        String username = userDetails.getUsername();
        Medico medico = medicoService.findMedicoByEmail(username);
        return medico;
    }

    // SAVE ADMIN
    @PostMapping("/register/medici")
    @PreAuthorize("hasAnyAuthority( 'MEDICO','RECEPTIONIST','ADMIN')")
    public Medico save(@RequestBody MedicoDTO medico, BindingResult validationResult,
                       @AuthenticationPrincipal UserDetails userDetails) throws MessagingException {
        return this.medicoService.save(medico);
    }

    //FIND BY ID
    @GetMapping("/{id}")

    public Medico getAdminById(@PathVariable UUID id) throws MessagingException {
        return medicoService.findMedicoByIdMedico(id);

    }

    //UPDATE MEDICO
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN','RECEPTIONIST')")
    public Medico updateAdmin(@PathVariable UUID id, @RequestBody MedicoUpdateDTO payload) throws MessagingException {
        return this.medicoService.findAndUpdate(id, payload);
    }

    // CANCELLA
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN','RECEPTIONIST')")
    public void deleteAdmin(@PathVariable UUID id) throws MessagingException {
        this.medicoService.findAndDelete(id);
    }

    // RESET PASSWORD
    @PostMapping("/{id}/reset-password")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MEDICO')")
    public Medico resetPassword(@PathVariable UUID id, @RequestBody PasswordResetDTO passwordResetDTO) throws BadRequestException {
        return this.medicoService.resetPassword(id, passwordResetDTO);

    }
}
