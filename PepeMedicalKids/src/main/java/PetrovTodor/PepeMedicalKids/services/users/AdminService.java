package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.entities.users.Admin;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.AdminDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetDTO;
import PetrovTodor.PepeMedicalKids.repositorys.users.AdminRepository;
import PetrovTodor.PepeMedicalKids.services.emeil.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;


    //SAVE
    public Admin saveAdmin(AdminDTO admin) throws MessagingException {
        String ultimoCodice = adminRepository.findMaxCodAdmin();


        Admin nuovoAdmin = new Admin(
                admin.codiceFiscale(),
                admin.nome(),
                admin.cognome(),
                admin.dataDiNascita(),
                admin.luogoDiNascita(),
                admin.email(),
                admin.password(),
                admin.numeroDiTelefono()
        );
        nuovoAdmin.generaCodice(ultimoCodice);
        // Crea l'email HTML
        String email = admin.email();
        String oggetto = "Account Creato Correttamente!";
        String htmlText = "<html>"
                + "<body>"
                + "<h1 style='color: #4CAF50;'>Benvenuto su Pepe Medical Kids!</h1>"
                + "<p>Ciao <b>" + admin.nome() + " " + admin.cognome() + "</b>,</p>"
                + "<p>Grazie di esserti registrato. Siamo felici di averti con noi.</p>"
                + "<p><b>Dettagli account:</b></p>"
                + "<ul>"
                + "<li><b>Username:</b> " + admin.email() + "</li>"
                + "<li><b>Password:</b> " + admin.password() + "</li>"
                + "</ul>"
                + "<p>Accedi al tuo account <a href='https://www.pepemedicalkids.com/login'>qui</a> e inizia a esplorare i nostri servizi!</p>"
                + "<br><p>Ti aspettiamo presto!</p>"
                + "<p>Il Team di Pepe Medical Kids</p>"
                + "</body>"
                + "</html>";

        // Usa il servizio email per inviare il messaggio HTML
        emailService.sendHtmlMessage(email, oggetto, htmlText);
        return adminRepository.save(nuovoAdmin);
    }

    //FIND BY ID
    public Admin findById(UUID idAdmin) {
        return this.adminRepository.findById(idAdmin)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore trovato con il seguente codice: " + idAdmin + "!"));
    }

    //FIND BY COD.ADMIN
    public Admin findByCodAdmin(String codAdmin) {
        return this.adminRepository.findAdminByCodAdmin(codAdmin)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore Trovato cin il seguente codice: " + codAdmin + "!"));

    }

    //FIND BY NAME
    public Admin findByName(String nome) {
        return this.adminRepository.findAdminByNome(nome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore Trovato cin il seguente nome: " + nome + "!"));

    }

    //FIND BY COGNOME
    public Admin findByCognome(String cognome) {
        return this.adminRepository.findAdminByCognome(cognome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore Trovato cin il seguente nome: " + cognome + "!"));

    }

    //FIND BY COD.FISLCALE
    public Admin findByCodiceFiscale(String codiceFiscale) {
        return this.adminRepository.findAdminByCognome(codiceFiscale)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore Trovato cin il seguente nome: " + codiceFiscale + "!"));

    }

    //FIND AND UPDITE

    public Admin findAndUpdate(UUID idAdmin, AdminDTO body) {
        Admin found = findById(idAdmin);
        found.setCodiceFiscale(body.codiceFiscale());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setDataDiNascita(body.dataDiNascita());
        found.setLuogoDiNascita(body.luogoDiNascita());
        found.setNumeroDiTelefono(body.numeroDiTelefono());
        return this.adminRepository.save(found);
    }

    //FIND AND DELETE

    public void findAndDelete(UUID idAdmin) {
        Admin found = findById(idAdmin);
        this.adminRepository.delete(found);
    }

    public Admin resetPassword(UUID idAdmin, PasswordResetDTO passwordResetDTO) {
        Admin foundAdmin = findById(idAdmin);

        if (!passwordEncoder.matches(passwordResetDTO.oldPassword(), foundAdmin.getPassword())) {
            throw new IllegalArgumentException("La vecchia password non è corretta!");
        }

        String hashedNewPassword = passwordEncoder.encode(passwordResetDTO.newPassword());
        foundAdmin.setPassword(hashedNewPassword);
        String subject = "Cambio Mail";
        String text = "la mail è stata regolarmente cambiata!";
        Admin updatedAdmin = this.adminRepository.save(foundAdmin);

        // Invia un'email di conferma
        emailService.sendSimpleMessage(foundAdmin.getEmail(), subject, text);

        return updatedAdmin;
    }

}
