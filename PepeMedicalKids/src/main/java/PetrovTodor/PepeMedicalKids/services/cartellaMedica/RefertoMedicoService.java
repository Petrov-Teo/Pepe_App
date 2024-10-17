package PetrovTodor.PepeMedicalKids.services.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.RefertoMedico;
import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.cartellaMedica.RefertoMedicoDTO;
import PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica.RefertoMedicoRepository;
import PetrovTodor.PepeMedicalKids.services.users.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefertoMedicoService {
    @Autowired
    private RefertoMedicoRepository refertoMedicoRepository;
    @Autowired
    private MedicoService medicoService;

    //SAVE
    public RefertoMedico saveRefertoMedico(RefertoMedicoDTO body) {
        Medico medico = this.medicoService.findMedicoByCodMedico(body.Medico());
        String ultimoCodice = refertoMedicoRepository.findMaxCodRefertoMedico();

        RefertoMedico RefertoMedico = new RefertoMedico(
                body.tipoPrescrizione(),
                body.oggetto(),
                body.note(),
                medico
        );

        RefertoMedico.generaCodRefertoMedico(ultimoCodice);

        return this.refertoMedicoRepository.save(RefertoMedico);
    }

    //FIND BY ID
    public RefertoMedico findByID(UUID idRefertoMedico) {
        return this.refertoMedicoRepository.findById(idRefertoMedico)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Certificato Medico trovato con il seguente id: " + idRefertoMedico + "!"));
    }

    //FIND BY CODMEDICO
    public RefertoMedico findByCodRefertoMedico(String codRefertoMedico) {
        return this.refertoMedicoRepository.findByCodRefertoMedico(codRefertoMedico)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Certificato Medico trovato con il seguente id: " + codRefertoMedico + "!"));
    }

    //FIND AND UPDITE
    public RefertoMedico findAndUpdate(UUID idRefertoMedico, RefertoMedicoDTO body) {
        Medico medico = this.medicoService.findMedicoByCodMedico(body.Medico());
        RefertoMedico found = findByID(idRefertoMedico);
        found.setTipoPrescrizione(body.tipoPrescrizione());
        found.setNote(body.note());
        found.setOggetto(body.oggetto());
        found.setMedicoAutore(medico);
        return this.refertoMedicoRepository.save(found);
    }

    //FIND AND DELETE

    public void findAndDeleteRefertoMedico(UUID idRefertoMedico) {
        RefertoMedico found = findByID(idRefertoMedico);
        this.refertoMedicoRepository.delete(found);
    }
}
