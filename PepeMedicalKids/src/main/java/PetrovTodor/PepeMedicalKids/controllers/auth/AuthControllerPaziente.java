package PetrovTodor.PepeMedicalKids.controllers.auth;

import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import PetrovTodor.PepeMedicalKids.payload.user.LoginRequestDTO;
import PetrovTodor.PepeMedicalKids.payload.user.LoginResponseTokenDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetMailDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PazienteMaggiorenneDTO;
import PetrovTodor.PepeMedicalKids.services.auth.AuthService;
import PetrovTodor.PepeMedicalKids.services.users.PazienteService;
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
public class AuthControllerPaziente {
    @Autowired
    private PazienteService pazienteService;

    @Autowired
    private AuthService authService;


    @PostMapping("/register/pazienti")
    public Paziente save(@RequestBody PazienteMaggiorenneDTO pazienteMaggiorenneDTO) throws MessagingException {
        return this.pazienteService.savePazienteMaggiorenne(pazienteMaggiorenneDTO);
    }

    @PostMapping("/login/pazienti")
    public LoginResponseTokenDTO loginPaziente(@RequestBody LoginRequestDTO payload) throws MessagingException {
        return new LoginResponseTokenDTO(this.authService.controlloCredenzialiAndGenerazioneTokenPaziente(payload));
    }

    @PostMapping("/reset-password/paziente")
    public ResponseEntity<Paziente> resetPasswordByEmailPaziente(@RequestBody PasswordResetMailDTO passwordResetMailDTO) throws MessagingException {
        Paziente updatePaziente = pazienteService.resetPasswordConMail(passwordResetMailDTO);
        return new ResponseEntity<>(updatePaziente, HttpStatus.OK);
    }
}
