package PetrovTodor.PepeMedicalKids.controllers.auth;

import PetrovTodor.PepeMedicalKids.entities.users.Admin;
import PetrovTodor.PepeMedicalKids.payload.user.LoginRequestDTO;
import PetrovTodor.PepeMedicalKids.payload.user.LoginResponseTokenDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetMailDTO;
import PetrovTodor.PepeMedicalKids.services.auth.AuthService;
import PetrovTodor.PepeMedicalKids.services.users.AdminService;
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
public class AuthControllerMedico {
    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;


    @PostMapping("/login/medici")
    public LoginResponseTokenDTO loginMedico(@RequestBody LoginRequestDTO payload) throws MessagingException {
        return new LoginResponseTokenDTO(this.authService.controlloCredenzialiAndGenerazioneToken(payload));
    }

    @PostMapping("/reset-password/medico")
    public ResponseEntity<Admin> resetPasswordByEmailMedico(@RequestBody PasswordResetMailDTO passwordResetMailDTO) throws MessagingException {
        Admin updatedAdmin = adminService.resetPasswordConMail(passwordResetMailDTO);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

}
