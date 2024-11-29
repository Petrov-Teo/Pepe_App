package PetrovTodor.PepeMedicalKids.controllers.auth;

import PetrovTodor.PepeMedicalKids.entities.users.Receptionist;
import PetrovTodor.PepeMedicalKids.payload.user.LoginRequestDTO;
import PetrovTodor.PepeMedicalKids.payload.user.LoginResponseTokenDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetMailDTO;
import PetrovTodor.PepeMedicalKids.services.auth.AuthService;
import PetrovTodor.PepeMedicalKids.services.users.ReceptionistService;
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
public class AuthControllerReceptionist {
    @Autowired
    private AuthService authService;
    @Autowired
    private ReceptionistService receptionistService;


    @PostMapping("/login/receptionists")
    public LoginResponseTokenDTO loginReceptionist(@RequestBody LoginRequestDTO payload) throws MessagingException {
        return new LoginResponseTokenDTO(this.authService.controlloCredenzialiAndGenerazioneTokenReceptionist(payload));
    }

    @PostMapping("/reset-password/receptionists")
    public ResponseEntity<Receptionist> resetPasswordByEmailMedico(@RequestBody PasswordResetMailDTO passwordResetMailDTO) throws MessagingException {
        Receptionist updatedReceptionist = receptionistService.resetPasswordConMail(passwordResetMailDTO);
        return new ResponseEntity<>(updatedReceptionist, HttpStatus.OK);
    }
}
