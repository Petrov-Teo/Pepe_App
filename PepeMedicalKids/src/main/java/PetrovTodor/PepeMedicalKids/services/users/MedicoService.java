package PetrovTodor.PepeMedicalKids.services.users;

import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.user.MedicoDTO;
import PetrovTodor.PepeMedicalKids.payload.user.MedicoUpdateDTO;
import PetrovTodor.PepeMedicalKids.repositorys.users.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    public Medico findMedicoByIdMedico(UUID idMedico) {
        return this.medicoRepository.findById(idMedico)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico Trovato con il seguente id: " + idMedico + "!"));
    }

    //SEARCH BY NAME
    public Medico findMedicoByNome(String nome) {
        return this.medicoRepository.findMedicoByNome(nome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico trovato con il seguente nome : " + nome + "!"));
    }

    //SEARCH BY COD.MEDICO
    public Medico findMedicoByCodMedico(String codMedico) {
        return this.medicoRepository.findMedicoByCodMedico(codMedico)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico trovato con il seguente codice Medico : " + codMedico + "!"));
    }

    //SEARCH BY SURNAME
    public Medico findMedicoByCognome(String cognome) {
        return this.medicoRepository.findMedicoByCognome(cognome)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico trovato con il seguente cognome : " + cognome + "!"));
    }

    //SEARCH BY CODICE FISCALE
    public Medico findMedicoByCodiceFiscale(String codiceFiscale) {
        return this.medicoRepository.findMedicoByCodiceFiscale(codiceFiscale)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Medico trovato con il seguente Codice Fiscale : " + codiceFiscale + "!"));
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
                body.password(),
                body.numeroDiTelefono(),
                body.specializzazione(),
                body.iscrizioneAlboN());
        medico.setCodMedico(ultimoCodice);
        return this.medicoRepository.save(medico);

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

}
