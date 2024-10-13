package PetrovTodor.PepeMedicalKids.services.cartellaMedica;

import PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica.CertificatoMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificatoMedicoService {
    @Autowired
    private CertificatoMedicoRepository certificatoMedicoRepository;
}
