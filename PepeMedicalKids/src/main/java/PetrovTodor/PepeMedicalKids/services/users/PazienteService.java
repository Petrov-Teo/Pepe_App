package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import PetrovTodor.PepeMedicalKids.exceptions.EmailSendingException;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.user.PazienteMaggiorenneDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PazienteMinorenneDTO;
import PetrovTodor.PepeMedicalKids.repositorys.users.PazienteRepository;
import PetrovTodor.PepeMedicalKids.services.emeil.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PazienteService {
    @Autowired
    private PazienteRepository pazienteRepository;

    private GenitoreTutoreService genitoreTutoreService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //FIND ALL
    public Page<Paziente> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.pazienteRepository.findAll(pageable);
    }


    // SAVE MAGGIORENNE
    public Paziente savePazienteMaggiorenne(PazienteMaggiorenneDTO body) throws MessagingException {
        String ultimoCodice = pazienteRepository.findMaxPaziente();

        Paziente paziente = new Paziente(
                body.codiceFiscale(),
                body.nome(),
                body.cognome(),
                body.dataDiNascita(),
                body.luogoDiNascita(),
                body.email(),
                passwordEncoder.encode(body.password()),
                body.numeroDiTelefono(),
                body.note());
        paziente.generaCodice(ultimoCodice);

        Paziente savedPaziente = pazienteRepository.save(paziente);

        String email = paziente.getEmail();
        String oggetto = "Account Creato Correttamente!";
        String htmlText = generateWelcomeEmailHtml(paziente);

        try {
            emailService.sendHtmlMessage(email, oggetto, htmlText);
        } catch (MessagingException e) {

            throw new EmailSendingException("Impossibile inviare l'email di benvenuto");
        }

        return this.pazienteRepository.save(savedPaziente);
    }

    private String generateWelcomeEmailHtml(Paziente paziente) {
        return "<html>"
                + "<body>"
                + "<h1 style='color: #4CAF50;'>Benvenuto su Pepe Medical Kids!</h1>"
                + "<p>Ciao <b>" + paziente.getNome() + " " + paziente.getCognome() + "</b>,</p>"
                + "<p>Grazie di esserti registrato. Siamo felici di averti con noi.</p>"
                + "<p>Accedi al tuo account <a href='https://www.pepemedicalkids.com/login'>qui</a> e inizia a esplorare i nostri servizi!</p>"
                + "<br><p>Ti aspettiamo presto!</p>"
                + "<p>Il Team di Pepe Medical Kids</p>"
                + "</body>"
                + "</html>";
    }

    // SAVE MINORENNE
    public Paziente savePazienteMinorenne(PazienteMinorenneDTO body) {
        GenitoreTutore genitoreTutore = this.genitoreTutoreService.findByCodGenitore(String.valueOf(body.genitoreTutore()));
        Paziente paziente = new Paziente(body.codiceFiscale(),
                body.nome(),
                body.cognome(),
                body.dataDiNascita(),
                body.luogoDiNascita(),
                genitoreTutore,
                body.tipoTutore(),
                body.note());
        if (paziente.isMinorenne() && paziente.getGenitoreTutore() == null) {
            throw new IllegalArgumentException("Il paziente è minorenne e deve avere un genitore o tutore associato.");
        }
        String email = paziente.getEmail();
        String oggetto = "Account Creato Correttamente!";
        String htmlText = generateWelcomeEmailHtml(paziente);

        try {
            emailService.sendHtmlMessage(email, oggetto, htmlText);
        } catch (MessagingException e) {

            throw new EmailSendingException("Impossibile inviare l'email di benvenuto");
        }

        return this.pazienteRepository.save(paziente);
    }

    //FIND BY ID
    public Paziente findPazienteByID(UUID idPaziente) {
        return this.pazienteRepository.findById(idPaziente).
                orElseThrow(() -> new NotFoundException("Il Paziente con id: " + idPaziente + " non trovato!"));
    }

    //FIND BY COD_PAZIENTE
    public Paziente findPazienteByCodPaziente(String codPaziente) {
        return this.pazienteRepository.findPazienteByCodPaziente(codPaziente).
                orElseThrow(() -> new NotFoundException("Il Paziente con id: " + codPaziente + " non trovato!"));
    }

    //FIND AND UPDITE MINORENNE
    public Paziente findAndUpdateMinorenne(String codPaziente, PazienteMinorenneDTO body) {
        GenitoreTutore genitoreTutore = this.genitoreTutoreService.findByCodGenitore(String.valueOf(body.genitoreTutore()));
        Paziente found = findPazienteByCodPaziente(codPaziente);
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setDataDiNascita(body.dataDiNascita());
        found.setLuogoDiNascita(body.luogoDiNascita());
        found.setCodiceFiscale(body.codiceFiscale());
        found.setGenitoreTutore(genitoreTutore);
        found.setTipoTutore(body.tipoTutore());
        found.setNote(body.note());

        return this.pazienteRepository.save(found);
    }

    //FIND AND UPDITE MAGGIORENNE
    public Paziente findAndUpdateMaggiorenne(String codPaziente, PazienteMaggiorenneDTO body) {
        Paziente found = findPazienteByCodPaziente(codPaziente);
        found.setCodiceFiscale(body.codiceFiscale());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setDataDiNascita(body.dataDiNascita());
        found.setLuogoDiNascita(body.luogoDiNascita());
        found.setEmail(body.email());
        found.setPassword(body.password());
        found.setNumeroDiTelefono(body.numeroDiTelefono());
        found.setNote(body.note());

        return this.pazienteRepository.save(found);
    }

    //FIND AND DELETE
    public void findAndDelete(UUID idPaziente) {
        Paziente found = findPazienteByID(idPaziente);
        this.pazienteRepository.delete(found);
    }

    //FIND BY NAME
    public List<Paziente> findByNome(String nome) {
        List<Paziente> founds = this.pazienteRepository.findPazienteByNome(nome);
        if (founds.isEmpty()) {
            throw new NotFoundException("Nessun Paziente trovato con il nome: " + nome + "!");
        }
        return founds;
    }

    //FIND BY COGNOME
    public List<Paziente> findByCognome(String cognome) {
        List<Paziente> founds = this.pazienteRepository.findPazienteByCognome(cognome);
        if (founds.isEmpty()) {
            throw new NotFoundException("Nessun Paziente trovato con il nome: " + cognome + "!");
        }
        return founds;
    }

    //FIND BY CODICE FISCALE
    public Paziente findByCodiceFiscale(String codiceFiscale) {
        return this.pazienteRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(() -> new NotFoundException("Nessun Paziente trovato con seguente Codice Fiscale: " + codiceFiscale + "!"));
    }

    //FIND BY EMAIL
    public Paziente findByEmail(String email) {
        return this.pazienteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Nessun Paziente trovato con la seguente email: " + email + "!"));
    }


}
