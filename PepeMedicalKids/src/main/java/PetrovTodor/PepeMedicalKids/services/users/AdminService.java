package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.entities.users.Admin;
import PetrovTodor.PepeMedicalKids.exceptions.EmailSendingException;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.user.AdminDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetMailDTO;
import PetrovTodor.PepeMedicalKids.repositorys.users.AdminRepository;
import PetrovTodor.PepeMedicalKids.services.emeil.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //SAVE
    public Admin saveAdmin(AdminDTO admin) throws MessagingException {

        String ultimoCodice = adminRepository.findMaxCodAdmin();
        String password = generateTemporaryPassword();

        Admin nuovoAdmin = new Admin(
                admin.codiceFiscale(),
                admin.nome(),
                admin.cognome(),
                admin.dataDiNascita(),
                admin.luogoDiNascita(),
                admin.email(),
                passwordEncoder.encode(password),
                admin.numeroDiTelefono()
        );
        nuovoAdmin.setPasswordTemporanea(true);
        nuovoAdmin.generaCodice(ultimoCodice);

        Admin savedAdmin = adminRepository.save(nuovoAdmin);

        String email = admin.email();
        String oggetto = "Account Creato Correttamente!";
        String htmlText = generateWelcomeEmailHtml(admin, password);

        try {
            emailService.sendHtmlMessage(email, oggetto, htmlText);
        } catch (MessagingException e) {
            System.err.println("Errore nell'invio dell'email di benvenuto: " + e.getMessage());
        }

        return savedAdmin;
    }

    private String generateWelcomeEmailHtml(AdminDTO admin, String temporaryPassword) {
        return "<html>"
                + "<body>"
                + "<h1 style='color: #4CAF50;'>Benvenuto su Pepe Medical Kids!</h1>"
                + "<p>Ciao <b>" + admin.nome() + " " + admin.cognome() + "</b>,</p>"
                + "<p>Grazie di esserti registrato. Siamo felici di averti con noi.</p>"
                + "<p>La tua password temporanea è: <b>" + temporaryPassword + "</b></p>" // Aggiungi la password temporanea
                + "<p>Accedi al tuo account <a href='https://www.pepemedicalkids.com/login'>qui</a> e inizia a esplorare i nostri servizi!</p>"
                + "<br><p>Ti aspettiamo presto!</p>"
                + "<p>Il Team di Pepe Medical Kids</p>"
                + "</body>"
                + "</html>";
    }

    //FIND ALL
    public Page<Admin> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.adminRepository.findAll(pageable);
    }

    //FIND BY ID
    public Admin findById(UUID idAdmin) {
        return this.adminRepository.findById(idAdmin)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore trovato con il seguente codice: " + idAdmin + "!"));
    }

    //FIND BY COD.ADMIN
    public Admin findByCodAdmin(String codAdmin) {
        return this.adminRepository.findByCodAdmin(codAdmin)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore Trovato con il seguente codice: " + codAdmin + "!"));

    }

    //FIND BY NAME
    public Admin findByName(String nome) {
        return this.adminRepository.findByNome(nome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore Trovato con il seguente nome: " + nome + "!"));

    }

    //FIND BY COGNOME
    public Admin findByCognome(String cognome) {
        return this.adminRepository.findByCognome(cognome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore Trovato con il seguente Cognome: " + cognome + "!"));

    }

    //FIND BY COD.FISLCALE
    public Admin findByCodiceFiscale(String codiceFiscale) {
        return this.adminRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore Trovato con il seguente Codice Fiscale: " + codiceFiscale + "!"));

    }

    //FIND BY EMAIL
    public Admin findByEmail(String email) {
        return this.adminRepository.findByEmail(email)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Amministratore Trovato con il seguente mail: " + email + "!"));

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

    // RESET PASSWORD
    public Admin resetPassword(UUID idAdmin, PasswordResetDTO passwordResetDTO) {
        Admin foundAdmin = findById(idAdmin);

        if (!passwordEncoder.matches(passwordResetDTO.oldPassword(), foundAdmin.getPassword())) {
            throw new IllegalArgumentException("La vecchia password non è corretta!");
        }

        String hashedNewPassword = passwordEncoder.encode(passwordResetDTO.newPassword());
        foundAdmin.setPassword(hashedNewPassword);

        String subject = "Cambio Password";
        String text = "La tua password è stata cambiata con successo!";
        foundAdmin.setPasswordTemporanea(false);

        Admin updatedAdmin = this.adminRepository.save(foundAdmin);

        emailService.sendSimpleMessage(foundAdmin.getEmail(), subject, text);

        return updatedAdmin;
    }

    //RESET PASSWORD CON EMEIL
    public Admin resetPasswordConMail(PasswordResetMailDTO passwordResetMailDTO) {
        Admin foundAdmin = adminRepository.findByEmail(passwordResetMailDTO.email())
                .orElseThrow(() -> new NotFoundException("Nessun amministratore trovato con l'email: " + passwordResetMailDTO.email()));

        String temporaryPassword = generateTemporaryPassword();

        foundAdmin.setPassword(passwordEncoder.encode(temporaryPassword));
        foundAdmin.setPasswordTemporanea(true);
        adminRepository.save(foundAdmin);

        String subject = "Reset Password";
        String text = "La tua password provvisoria è: " + temporaryPassword +
                "\n\nTi invitiamo a cambiarla al tuo prossimo accesso.";

        try {
            emailService.sendSimpleMessage(foundAdmin.getEmail(), subject, text);
            System.out.println(("Email di reset password inviata a {}" + foundAdmin.getEmail()));
        } catch (EmailSendingException e) {
            throw new EmailSendingException("Impossibile inviare l'email con la password provvisoria.");
        }


        return foundAdmin;
    }

    private String generateTemporaryPassword() {

        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(rnd.nextInt(characters.length())));
        }

        return password.toString();
    }

}
