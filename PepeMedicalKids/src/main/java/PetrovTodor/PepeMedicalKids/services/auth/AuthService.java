package PetrovTodor.PepeMedicalKids.services.auth;

import PetrovTodor.PepeMedicalKids.entities.users.Admin;
import PetrovTodor.PepeMedicalKids.exceptions.UnauthorizedException;
import PetrovTodor.PepeMedicalKids.payload.user.LoginRequestDTO;
import PetrovTodor.PepeMedicalKids.security.JWTTools;
import PetrovTodor.PepeMedicalKids.services.users.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AdminService adminService;
    @Autowired
    PasswordEncoder bcrypt;
    @Autowired
    private JWTTools jwtTools;

    public String controlloCredenzialiAndGenerazioneToken(LoginRequestDTO payload) {
        Admin found = adminService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), found.getPassword())) {

            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Errate!");
        }

    }


}