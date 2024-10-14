package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.entities.users.Receptionist;
import PetrovTodor.PepeMedicalKids.exceptions.EmailSendingException;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetMailDTO;
import PetrovTodor.PepeMedicalKids.payload.user.ReceptionistDTO;
import PetrovTodor.PepeMedicalKids.repositorys.users.ReceptionistRepository;
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
public class ReceptionistService {
    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Receptionist saveReceptionist(ReceptionistDTO body) throws MessagingException {

        String ultimoCodice = receptionistRepository.findMaxCodReceptionist();

        Receptionist nuovoReceptionist = new Receptionist(
                body.codiceFiscale(),
                body.nome(),
                body.cognome(),
                body.dataDiNascita(),
                body.luogoDiNascita(),
                body.email(),
                passwordEncoder.encode(body.password()),
                body.numeroDiTelefono()
        );

        nuovoReceptionist.generaCodice(ultimoCodice);

        Receptionist savedReceptionist = receptionistRepository.save(nuovoReceptionist);

        String email = body.email();
        String oggetto = "Account Creato Correttamente!";
        String htmlText = generateWelcomeEmailHtml(body);

        try {
            emailService.sendHtmlMessage(email, oggetto, htmlText);
        } catch (MessagingException e) {
            System.err.println("Errore nell'invio dell'email di benvenuto: " + e.getMessage());
        }

        return savedReceptionist;
    }

    private String generateWelcomeEmailHtml(ReceptionistDTO admin) {
        return "<html>"
                + "<body>"
                + "<h1 style='color: #4CAF50;'>Benvenuto su Pepe Medical Kids!</h1>"
                + "<p>Ciao <b>" + admin.nome() + " " + admin.cognome() + "</b>,</p>"
                + "<p>Grazie di esserti registrato. Siamo felici di averti con noi.</p>"
                + "<p>Accedi al tuo account <a href='https://www.pepemedicalkids.com/login'>qui</a> e inizia a esplorare i nostri servizi!</p>"
                + "<br><p>Ti aspettiamo presto!</p>"
                + "<p>Il Team di Pepe Medical Kids</p>"
                + "</body>"
                + "</html>";
    }

    //FIND ALL
    public Page<Receptionist> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.receptionistRepository.findAll(pageable);
    }

    //FIND BY ID
    public Receptionist findById(UUID idReceptionist) {
        return this.receptionistRepository.findById(idReceptionist)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Receptionist trovato con il seguente codice: " + idReceptionist + "!"));
    }

    //FIND BY COD.ADMIN
    public Receptionist findByCodAdmin(String codReceptionist) {
        return this.receptionistRepository.findByCodReceptionist(codReceptionist)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Receptionist Trovato con il seguente codice: " + codReceptionist + "!"));

    }

    //FIND BY NAME
    public Receptionist findByName(String nome) {
        return this.receptionistRepository.findByNome(nome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Receptionist Trovato con il seguente nome: " + nome + "!"));

    }

    //FIND BY COGNOME
    public Receptionist findByCognome(String cognome) {
        return this.receptionistRepository.findByCognome(cognome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Receptionist Trovato con il seguente Cognome: " + cognome + "!"));

    }

    //FIND BY COD.FISLCALE
    public Receptionist findByCodiceFiscale(String codiceFiscale) {
        return this.receptionistRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Receptionist Trovato con il seguente Codice Fiscale: " + codiceFiscale + "!"));

    }

    //FIND BY EMAIL
    public Receptionist findByEmail(String email) {
        return this.receptionistRepository.findByEmail(email)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Receptionist Trovato con il seguente mail: " + email + "!"));

    }

    public Receptionist findAndUpdate(UUID idReceptionist, ReceptionistDTO body) {
        Receptionist found = findById(idReceptionist);
        found.setCodiceFiscale(body.codiceFiscale());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setDataDiNascita(body.dataDiNascita());
        found.setLuogoDiNascita(body.luogoDiNascita());
        found.setNumeroDiTelefono(body.numeroDiTelefono());
        return this.receptionistRepository.save(found);
    }

    //FIND AND DELETE

    public void findAndDelete(UUID idReceptionist) {
        Receptionist found = findById(idReceptionist);
        this.receptionistRepository.delete(found);
    }

    // RESET PASSWORD
    public Receptionist resetPassword(UUID idAdmin, PasswordResetDTO passwordResetDTO) {
        Receptionist foundAdmin = findById(idAdmin);

        if (!passwordEncoder.matches(passwordResetDTO.oldPassword(), foundAdmin.getPassword())) {
            throw new IllegalArgumentException("La vecchia password non è corretta!");
        }

        String hashedNewPassword = passwordEncoder.encode(passwordResetDTO.newPassword());
        foundAdmin.setPassword(hashedNewPassword);

        String subject = "Cambio Password";
        String text = "La tua password è stata cambiata con successo!";

        Receptionist updatedAdmin = this.receptionistRepository.save(foundAdmin);

        emailService.sendSimpleMessage(foundAdmin.getEmail(), subject, text);

        return updatedAdmin;
    }

    //RESET PASSWORD CON EMEIL
    public Receptionist resetPasswordConMail(PasswordResetMailDTO passwordResetMailDTO) {
        Receptionist foundReceptionist = receptionistRepository.findByEmail(passwordResetMailDTO.email())
                .orElseThrow(() -> new NotFoundException("Nessun amministratore trovato con l'email: " + passwordResetMailDTO.email()));

        String temporaryPassword = generateTemporaryPassword();

        foundReceptionist.setPassword(passwordEncoder.encode(temporaryPassword));
        receptionistRepository.save(foundReceptionist);

        String subject = "Reset Password";
        String text = "La tua password provvisoria è: " + temporaryPassword +
                "\n\nTi invitiamo a cambiarla al tuo prossimo accesso.";

        try {
            emailService.sendSimpleMessage(foundReceptionist.getEmail(), subject, text);
            System.out.println(("Email di reset password inviata a {}" + foundReceptionist.getEmail()));
        } catch (EmailSendingException e) {
            throw new EmailSendingException("Impossibile inviare l'email con la password provvisoria.");
        }

        return foundReceptionist;
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


