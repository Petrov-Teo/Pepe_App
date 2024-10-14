package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.exceptions.EmailSendingException;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.user.MedicoDTO;
import PetrovTodor.PepeMedicalKids.payload.user.MedicoUpdateDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PasswordResetMailDTO;
import PetrovTodor.PepeMedicalKids.repositorys.users.MedicoRepository;
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
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    //FIND ALL
    public Page<Medico> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.medicoRepository.findAll(pageable);
    }

    //FIND BY ID
    public Medico findMedicoByIdMedico(UUID idMedico) {
        return this.medicoRepository.findById(idMedico)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico Trovato con il seguente id: " + idMedico + "!"));
    }

    //SEARCH BY NAME
    public Medico findMedicoByNome(String nome) {
        return this.medicoRepository.findByNome(nome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico trovato con il seguente nome : " + nome + "!"));
    }

    //SEARCH BY COD.MEDICO
    public Medico findMedicoByCodMedico(String codMedico) {
        return this.medicoRepository.findByCodMedico(codMedico)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico trovato con il seguente codice Medico : " + codMedico + "!"));
    }

    //SEARCH BY SURNAME
    public Medico findMedicoByCognome(String cognome) {
        return this.medicoRepository.findByCognome(cognome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico trovato con il seguente cognome : " + cognome + "!"));
    }

    //SEARCH BY CODICE FISCALE
    public Medico findMedicoByCodiceFiscale(String codiceFiscale) {
        return this.medicoRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico trovato con il seguente Codice Fiscale : " + codiceFiscale + "!"));
    }

    //SEARCH BY CODICE FISCALE
    public Medico findMedicoByEmail(String email) {
        return this.medicoRepository.findByEmail(email)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico trovato con la seguente Email : " + email + "!"));
    }

    //SAVE
    public Medico save(MedicoDTO body) {
        String ultimoCodice = this.medicoRepository.findMaxCodMedico();

        Medico medico = new Medico(
                body.codiceFiscale(),
                body.nome(),
                body.cognome(),
                body.dataDiNascita(),
                body.luogoDiNascita(),
                body.email(),
                passwordEncoder.encode(body.password()),
                body.numeroDiTelefono(),
                body.specializzazione(),
                body.iscrizioneAlboN());
        medico.setCodMedico(ultimoCodice);
        Medico savedMedico = medicoRepository.save(medico);

        String email = body.email();
        String oggetto = "Account Creato Correttamente!";
        String htmlText = generateWelcomeEmailHtml(body);

        try {
            emailService.sendHtmlMessage(email, oggetto, htmlText);
        } catch (MessagingException e) {
            System.err.println("Errore nell'invio dell'email di benvenuto: " + e.getMessage());
        }

        return savedMedico;

    }

    private String generateWelcomeEmailHtml(MedicoDTO admin) {
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

    //UPDATE

    public Medico findAndUpdate(UUID idMedico, MedicoUpdateDTO body) {
        Medico found = findMedicoByIdMedico(idMedico);
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setDataDiNascita(body.dataDiNascita());
        found.setLuogoDiNascita(body.luogoDiNascita());
        found.setNumeroDiTelefono(body.numeroDiTelefono());
        found.setSpecializzazione(body.specializzazione());
        found.setIscrizioneAlboN(body.iscrizioneAlboN());
        return this.medicoRepository.save(found);
    }

    // DELETE

    public void findAndDelete(UUID idMedico) {
        Medico found = findMedicoByIdMedico(idMedico);
        this.medicoRepository.delete(found);
    }

    // RESET PASSWORD
    public Medico resetPassword(UUID idAdmin, PasswordResetDTO passwordResetDTO) {
        Medico foundMedico = findMedicoByIdMedico(idAdmin);

        if (!passwordEncoder.matches(passwordResetDTO.oldPassword(), foundMedico.getPassword())) {
            throw new IllegalArgumentException("La vecchia password non è corretta!");
        }

        String hashedNewPassword = passwordEncoder.encode(passwordResetDTO.newPassword());
        foundMedico.setPassword(hashedNewPassword);

        String subject = "Cambio Password";
        String text = "La tua password è stata cambiata con successo!";

        Medico updatedMedico = this.medicoRepository.save(foundMedico);

        emailService.sendSimpleMessage(foundMedico.getEmail(), subject, text);

        return updatedMedico;
    }

    //RESET PASSWORD CON EMEIL
    public Medico resetPasswordConMail(PasswordResetMailDTO passwordResetMailDTO) {
        Medico foundMedico = medicoRepository.findByEmail(passwordResetMailDTO.email())
                .orElseThrow(() -> new NotFoundException("Nessun amministratore trovato con l'email: " + passwordResetMailDTO.email()));

        String temporaryPassword = generateTemporaryPassword();

        foundMedico.setPassword(passwordEncoder.encode(temporaryPassword));
        medicoRepository.save(foundMedico);

        String subject = "Reset Password";
        String text = "La tua password provvisoria è: " + temporaryPassword +
                "\n\nTi invitiamo a cambiarla al tuo prossimo accesso.";

        try {
            emailService.sendSimpleMessage(foundMedico.getEmail(), subject, text);
            System.out.println(("Email di reset password inviata a {}" + foundMedico.getEmail()));
        } catch (EmailSendingException e) {
            throw new EmailSendingException("Impossibile inviare l'email con la password provvisoria.");
        }

        return foundMedico;
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
