package PetrovTodor.PepeMedicalKids.services.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.CertificatoMedico;
import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.cartellaMedica.CertificatoMedicoDTO;
import PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica.CertificatoMedicoRepository;
import PetrovTodor.PepeMedicalKids.services.users.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CertificatoMedicoService {
    @Autowired
    private CertificatoMedicoRepository certificatoMedicoRepository;
    @Autowired
    private MedicoService medicoService;

    //SAVE
    public CertificatoMedico saveCertificatoMedico(CertificatoMedicoDTO body) {
        Medico medico = this.medicoService.findMedicoByCodMedico(body.Medico());
        String ultimoCodice = certificatoMedicoRepository.findMaxCodiceCertificatoMedico();

        CertificatoMedico certificatoMedico = new CertificatoMedico(
                body.tipoPrescrizione(),
                body.note(),
                body.giorniPrognosi(),
                medico);

        certificatoMedico.generaCodiceCertificatoMedico(ultimoCodice);

        return this.certificatoMedicoRepository.save(certificatoMedico);
    }

    //FIND BY ID
    public CertificatoMedico findByID(UUID idCertificatoMedico) {
        return this.certificatoMedicoRepository.findById(idCertificatoMedico)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Certificato Medico trovato con il seguente id: " + idCertificatoMedico + "!"));
    }

    //FIND BY CODMEDICO
    public CertificatoMedico findByCodCertificatoMedico(String codCertificatoMedico) {
        return this.certificatoMedicoRepository.findByCodCertificatoMedico(codCertificatoMedico)
                .orElseThrow(()
                        -> new NotFoundException("Nessun Certificato Medico trovato con il seguente id: " + codCertificatoMedico + "!"));
    }

    //FIND AND UPDITE
    public CertificatoMedico findAndUpdate(UUID idCertificatoMedico, CertificatoMedicoDTO body) {
        Medico medico = this.medicoService.findMedicoByCodMedico(body.Medico());
        CertificatoMedico found = findByID(idCertificatoMedico);
        found.setTipoPrescrizione(body.tipoPrescrizione());
        found.setNote(body.note());
        found.setGiorniPrognosi(body.giorniPrognosi());
        found.setMedicoAutore(medico);
        return this.certificatoMedicoRepository.save(found);
    }

    //FIND AND DELETE

    public void findAndDeleteCertificatoMedico(UUID idCertificatoMedico) {
        CertificatoMedico found = findByID(idCertificatoMedico);
        this.certificatoMedicoRepository.delete(found);
    }

}
