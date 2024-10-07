package PetrovTodor.PepeMedicalKids.services;

import PetrovTodor.PepeMedicalKids.entities.users.Admin;
import PetrovTodor.PepeMedicalKids.repositorys.users.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    public Admin creaNuovoAdmin(Admin admin) {
        String ultimoCodice = adminRepository.findMaxCodAdmin();
        int codiceNumerico = 101;  // Codice base se nessun codice esiste

        // Se esiste un ultimo codice, estrai la parte numerica e incrementala
        if (ultimoCodice != null) {
            String parteNumerica = ultimoCodice.substring(1); // Rimuove la prima lettera (es: 'A101' -> '101')
            codiceNumerico = Integer.parseInt(parteNumerica) + 1;  // Incrementa la parte numerica
        }

        // Genera il codice concatenato (es: 'A102')
        char primaLetteraRuolo = admin.getRuolo().name().charAt(0);
        String codiceConcatenato = primaLetteraRuolo + String.valueOf(codiceNumerico);

        // Crea il nuovo admin
        Admin nuovoAdmin = new Admin(
                admin.getCodiceFiscale(),
                admin.getNome(),
                admin.getCognome(),
                admin.getDataDiNascita(),
                admin.getLuogoDiNascita(),
                admin.getRuolo(),
                admin.getEmail(),
                admin.getPassword(),
                admin.getNumeroDiTelefono(),
                codiceConcatenato
        );

        return adminRepository.save(nuovoAdmin);
    }

    public Optional<Admin> findById(UUID idAdmin) {
        return this.adminRepository.findById(UUID.fromString(String.valueOf(idAdmin)));
    }
}
