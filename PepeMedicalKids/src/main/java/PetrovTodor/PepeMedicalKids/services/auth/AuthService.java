package PetrovTodor.PepeMedicalKids.services.auth;

import PetrovTodor.PepeMedicalKids.entities.users.*;
import PetrovTodor.PepeMedicalKids.exceptions.UnauthorizedException;
import PetrovTodor.PepeMedicalKids.payload.user.LoginRequestDTO;
import PetrovTodor.PepeMedicalKids.security.JWTTools;
import PetrovTodor.PepeMedicalKids.services.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private GenitoreTutoreService genitoreTutoreService;
    @Autowired
    private PazienteService pazienteService;
    @Autowired
    private ReceptionistService receptionistService;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private JWTTools jwtTools;

    public String controlloCredenzialiAndGenerazioneTokenADmin(LoginRequestDTO payload) {
        Admin found = adminService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), found.getPassword())) {

            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Errate!");
        }
    }

    public String controlloCredenzialiAndGenerazioneTokenMedico(LoginRequestDTO payload) {
        Medico found = medicoService.findMedicoByEmail(payload.email());
        if (bcrypt.matches(payload.password(), found.getPassword())) {

            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Errate!");
        }
    }

    public String controlloCredenzialiAndGenerazioneTokenGenitore(LoginRequestDTO payload) {
        GenitoreTutore found = genitoreTutoreService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), found.getPassword())) {

            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Errate!");
        }
    }

    public String controlloCredenzialiAndGenerazioneTokenPaziente(LoginRequestDTO payload) {
        Paziente found = pazienteService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), found.getPassword())) {

            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Errate!");
        }
    }

    public String controlloCredenzialiAndGenerazioneTokenReceptionist(LoginRequestDTO payload) {
        Receptionist found = receptionistService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), found.getPassword())) {

            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Errate!");
        }
    }

}
