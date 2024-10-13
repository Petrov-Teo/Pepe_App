package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.user.GenitoreTutoreDTO;
import PetrovTodor.PepeMedicalKids.repositorys.users.GenitoreTutoreRepository;
import PetrovTodor.PepeMedicalKids.services.emeil.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GenitoreTutoreService {
    @Autowired
    private GenitoreTutoreRepository genitoreTutoreRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

   
    private PazienteService pazienteService;


    //SAVE
    public GenitoreTutore saveGenitoreTutore(GenitoreTutoreDTO body) throws MessagingException {

        String ultimoCodice = genitoreTutoreRepository.findMaxCodGenitore();

        List<Paziente> pazientiAssociati = new ArrayList<>();

        for (Paziente codPaziente : body.pazienti()) {
            Paziente foundPaziente = pazienteService.findPazienteByCodPaziente(String.valueOf(codPaziente));
            if (foundPaziente != null) {
                pazientiAssociati.add(foundPaziente);
            } else {
                throw new NotFoundException("Paziente con codice " + codPaziente + " non trovato");
            }
        }


        GenitoreTutore genitoreTutore = new GenitoreTutore(
                body.codiceFiscale(),
                body.nome(),
                body.cognome(),
                body.dataDiNascita(),
                body.luogoDiNascita(),
                body.email(),
                passwordEncoder.encode(body.password()),
                body.numeroDiTelefono(),
                body.note(),
                body.tipoTutore(),
                pazientiAssociati
        );


        genitoreTutore.generaCodice(ultimoCodice);


        GenitoreTutore savedGenitore = genitoreTutoreRepository.save(genitoreTutore);

        String email = body.email();
        String oggetto = "Account Creato Correttamente!";
        String htmlText = generateWelcomeEmailHtml(body);

        try {
            emailService.sendHtmlMessage(email, oggetto, htmlText);
        } catch (MessagingException e) {
            System.err.println("Errore nell'invio dell'email di benvenuto: " + e.getMessage());
        }

        return savedGenitore;
    }

    // GENERA EMAIL IN HTML
    private String generateWelcomeEmailHtml(GenitoreTutoreDTO body) {
        return "<html>"
                + "<body>"
                + "<h1 style='color: #4CAF50;'>Benvenuto su Pepe Medical Kids!</h1>"
                + "<p>Ciao <b>" + body.nome() + " " + body.cognome() + "</b>,</p>"
                + "<p>Grazie di esserti registrato. Siamo felici di averti con noi.</p>"
                + "<p>Accedi al tuo account <a href='https://www.pepemedicalkids.com/login'>qui</a> e inizia a esplorare i nostri servizi!</p>"
                + "<br><p>Ti aspettiamo presto!</p>"
                + "<p>Il Team di Pepe Medical Kids</p>"
                + "</body>"
                + "</html>";
    }

    //FIND ALL
    public Page<GenitoreTutore> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.genitoreTutoreRepository.findAll(pageable);

    }

    //FIND BY ID
    public GenitoreTutore findById(UUID idGenitore) {
        return this.genitoreTutoreRepository.findById(idGenitore)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Genitore-Tutore trovato con il seguente codice: " + idGenitore + "!"));
    }

    //FIND BY COD.ADMIN
    public GenitoreTutore findByCodGenitore(String codGenitore) {
        return this.genitoreTutoreRepository.findByCodGenitore(codGenitore)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Genitore-Tutore trovato con il seguente codice: " + codGenitore + "!"));

    }

    //FIND BY NAME
    public GenitoreTutore findByName(String nome) {
        return this.genitoreTutoreRepository.findByNome(nome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Genitore-Tutore Trovato con il seguente nome: " + nome + "!"));

    }

    //FIND BY COGNOME
    public GenitoreTutore findByCognome(String cognome) {
        return this.genitoreTutoreRepository.findByCognome(cognome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Genitore-Tutore Trovato con il seguente Cognome: " + cognome + "!"));

    }

    //FIND BY COD.FISLCALE
    public GenitoreTutore findByCodiceFiscale(String codiceFiscale) {
        return this.genitoreTutoreRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Genitore-Tutore Trovato con il seguente Codice Fiscale: " + codiceFiscale + "!"));

    }

    //FIND BY EMAIL
    public GenitoreTutore findByEmail(String email) {
        return this.genitoreTutoreRepository.findByEmail(email)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Genitore-Tutore Trovato con il seguente mail: " + email + "!"));

    }

//TODO: DA IMPLEMENTARE update and delete ;

}
