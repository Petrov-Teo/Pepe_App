package PetrovTodor.PepeMedicalKids.controllers.auth;

import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.payload.user.GenitoreTutoreDTO;
import PetrovTodor.PepeMedicalKids.payload.user.LoginRequestDTO;
import PetrovTodor.PepeMedicalKids.payload.user.LoginResponseTokenDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetMailDTO;
import PetrovTodor.PepeMedicalKids.services.auth.AuthService;
import PetrovTodor.PepeMedicalKids.services.users.GenitoreTutoreService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthControllerGenitore {
    @Autowired
    private GenitoreTutoreService genitoreTutoreService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register/genitori")
    public GenitoreTutore save(@RequestBody GenitoreTutoreDTO genitore) throws MessagingException {
        return this.genitoreTutoreService.saveGenitoreTutore(genitore);
    }

    @PostMapping("/login/tutori")
    public LoginResponseTokenDTO loginGeniore(@RequestBody LoginRequestDTO payload) throws MessagingException {
        return new LoginResponseTokenDTO(this.authService.controlloCredenzialiAndGenerazioneTokenGenitore(payload));
    }

    @PostMapping("/reset-password/tutor")
    public ResponseEntity<GenitoreTutore> resetPasswordByEmailAdmin(@RequestBody PasswordResetMailDTO passwordResetMailDTO) throws MessagingException {
        GenitoreTutore updatedAdmin = genitoreTutoreService.resetPasswordConMail(passwordResetMailDTO);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }
}
