package PetrovTodor.PepeMedicalKids.controllers.users;

import PetrovTodor.PepeMedicalKids.entities.users.Admin;
import PetrovTodor.PepeMedicalKids.exceptions.BadRequestException;
import PetrovTodor.PepeMedicalKids.payload.user.AdminDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetDTO;
import PetrovTodor.PepeMedicalKids.services.users.AdminService;
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
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //FIND ALL
    @GetMapping
    @PreAuthorize("hasAnyAuthority(, 'ADMIN')")
    public Page<Admin> findAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "codAdmin") String sorteBy) {

        return adminService.findAll(page, size, sorteBy);
    }

    // FIND ME
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority( 'ADMIN')")
    public Admin findMe(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("L'utente non è autenticato!");
        }
        String username = userDetails.getUsername();
        Admin admin = adminService.findByEmail(username);
        return admin;
    }

    // SAVE ADMIN
    @PostMapping("/register/admins")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Admin save(@RequestBody AdminDTO admin, BindingResult validationResult,
                      @AuthenticationPrincipal UserDetails userDetails) throws MessagingException {
        return this.adminService.saveAdmin(admin);
    }

    //FIND BY ID
    @GetMapping("/{id}")

    public Admin getAdminById(@PathVariable UUID id) throws MessagingException {
        return adminService.findById(id);

    }

    //UPDATE ADMIN
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin updateAdmin(@PathVariable UUID id, @RequestBody AdminDTO payload) throws MessagingException {
        return this.adminService.findAndUpdate(id, payload);
    }

    // CANCELLA
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdmin(@PathVariable UUID id) throws MessagingException {
        this.adminService.findAndDelete(id);
    }

    // RESET PASSWORD
    @PostMapping("/{id}/reset-password")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin resetPassword(@PathVariable UUID id, @RequestBody PasswordResetDTO passwordResetDTO) throws BadRequestException {
        return this.adminService.resetPassword(id, passwordResetDTO);

    }
}
