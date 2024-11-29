package PetrovTodor.PepeMedicalKids.controllers.users;

import PetrovTodor.PepeMedicalKids.entities.users.Receptionist;
import PetrovTodor.PepeMedicalKids.exceptions.BadRequestException;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetDTO;
import PetrovTodor.PepeMedicalKids.payload.user.ReceptionistDTO;
import PetrovTodor.PepeMedicalKids.services.users.ReceptionistService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/receptionists")
public class ReceptionistController {
    @Autowired
    private ReceptionistService receptionistService;

    //FIND ALL
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<Receptionist> findAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "codReceptionist") String sorteBy) {

        return this.receptionistService.findAll(page, size, sorteBy);
    }

    // FIND ME
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST')")
    public Receptionist findMe(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("L'utente non è autenticato!");
        }
        String username = userDetails.getUsername();
        Receptionist receptionist = receptionistService.findByEmail(username);
        return receptionist;
    }

    // SAVE
    @PostMapping("/register/receptionists")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Receptionist save(@RequestBody ReceptionistDTO receptionist, BindingResult validationResult,
                             @AuthenticationPrincipal UserDetails userDetails) throws MessagingException {
        return this.receptionistService.saveReceptionist(receptionist);
    }

    //FIND BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Receptionist getReceptionistById(@PathVariable UUID id) throws MessagingException {
        return this.receptionistService.findById(id);

    }

    //UPDATE RECEPTIONIST
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST','ADMIN')")
    public Receptionist updateReceptionist(@PathVariable UUID id, @RequestBody ReceptionistDTO payload) throws MessagingException {
        return this.receptionistService.findAndUpdate(id, payload);
    }

    // CANCELLA
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteReceptionist(@PathVariable UUID id) throws MessagingException {
        this.receptionistService.findAndDelete(id);
    }

    // RESET PASSWORD
    @PostMapping("/reset-password/{idMedico}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('RECEPTIONIST')")
    public Receptionist resetPassword(@PathVariable UUID idReceptionist,
                                      @RequestBody PasswordResetDTO passwordResetDTO
    ) throws BadRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User: " + authentication.getName().isEmpty());
        System.out.println("Authorities: " + authentication.getAuthorities().toString());
        return this.receptionistService.resetPassword(idReceptionist, passwordResetDTO);
    }

}
