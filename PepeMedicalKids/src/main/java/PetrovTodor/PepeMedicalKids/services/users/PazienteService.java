package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.entities.users.GenitoreTutore;
import PetrovTodor.PepeMedicalKids.entities.users.Paziente;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.user.PazienteMaggiorenneDTO;
import PetrovTodor.PepeMedicalKids.payload.user.PazienteMinorenneDTO;
import PetrovTodor.PepeMedicalKids.repositorys.users.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PazienteService {
    @Autowired
    private PazienteRepository pazienteRepository;
    @Autowired
    private GenitoreTutoreService genitoreTutoreService;


    public Paziente savePazienteMaggiorenne(PazienteMaggiorenneDTO body) {
        Paziente paziente = new Paziente(body.codiceFiscale(),
                body.nome(),
                body.cognome(),
                body.dataDiNascita(),
                body.luogoDiNascita(),
                body.email(),
                body.Password(),
                body.numeroDiTelefono(),
                body.note());
        if (paziente.isMinorenne() && paziente.getGenitoreTutore() == null) {
            throw new IllegalArgumentException("Il paziente è minorenne e deve avere un genitore o tutore associato.");
        }
        return this.pazienteRepository.save(paziente);
    }

    public Paziente savePazienteMinorenne(PazienteMinorenneDTO body) {
        GenitoreTutore genitoreTutore = this.genitoreTutoreService.findByCodGenitore(String.valueOf(body.genitoreTutore()));
        Paziente paziente = new Paziente(body.codiceFiscale(),
                body.nome(),
                body.cognome(),
                body.dataDiNascita(),
                body.luogoDiNascita(),
                genitoreTutore,
                body.note());
        if (paziente.isMinorenne() && paziente.getGenitoreTutore() == null) {
            throw new IllegalArgumentException("Il paziente è minorenne e deve avere un genitore o tutore associato.");
        }
        return this.pazienteRepository.save(paziente);
    }

    public Paziente findPazienteByID(UUID idPaziente) {
        return this.pazienteRepository.findById(idPaziente).
                orElseThrow(() -> new NotFoundException("Il Paziente con id: " + idPaziente + " non trovato!"));
    }

    public Paziente findPazienteByCodPaziente(String codPaziente) {
        return this.pazienteRepository.findPazienteByCodPaziente(codPaziente).
                orElseThrow(() -> new NotFoundException("Il Paziente con id: " + codPaziente + " non trovato!"));
    }

    public Paziente findAndUpdateMinorenne(String codPaziente, PazienteMinorenneDTO body) {
        GenitoreTutore genitoreTutore = this.genitoreTutoreService.findByCodGenitore(String.valueOf(body.genitoreTutore()));
        Paziente found = findPazienteByCodPaziente(codPaziente);
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setDataDiNascita(body.dataDiNascita());
        found.setLuogoDiNascita(body.luogoDiNascita());
        found.setCodiceFiscale(body.codiceFiscale());
        found.setGenitoreTutore(genitoreTutore);
        found.setNote(body.note());

        return this.pazienteRepository.save(found);
    }

    public Paziente findAndUpdateMaggiorenne(String codPaziente, PazienteMaggiorenneDTO body) {
        Paziente found = findPazienteByCodPaziente(codPaziente);
        found.setCodiceFiscale(body.codiceFiscale());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setDataDiNascita(body.dataDiNascita());
        found.setLuogoDiNascita(body.luogoDiNascita());
        found.setEmail(body.email());
        found.setPassword(body.Password());
        found.setNumeroDiTelefono(body.numeroDiTelefono());
        found.setNote(body.note());

        return this.pazienteRepository.save(found);
    }

    public void findAndDelete(UUID idPaziente) {
        Paziente found = findPazienteByID(idPaziente);
        this.pazienteRepository.delete(found);
    }

    public List<Paziente> findByNome(String nome) {
        List<Paziente> founds = this.pazienteRepository.findPazienteByNome(nome);
        if (founds.isEmpty()) {
            throw new NotFoundException("Nessun Paziente trovato con il nome: " + nome + "!");
        }
        return founds;
    }

    public List<Paziente> findByCognome(String cognome) {
        List<Paziente> founds = this.pazienteRepository.findPazienteByCognome(cognome);
        if (founds.isEmpty()) {
            throw new NotFoundException("Nessun Paziente trovato con il nome: " + cognome + "!");
        }
        return founds;
    }

    public Paziente findByCodiceFiscale(String codiceFiscale) {
        Paziente found = this.pazienteRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(() -> new NotFoundException("Nessun Paziente trovato con seguente Codice Fiscale: " + codiceFiscale + "!"));
        return found;
    }


}
