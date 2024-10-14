package PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.CertificatoMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificatoMedicoRepository extends JpaRepository<CertificatoMedico, UUID> {
    @Query("SELECT MAX(a.codCertificatoMedico) FROM CertificatoMedico a")
    String findMaxCodiceCertificatoMedico();

    Optional<CertificatoMedico> findByCodCertificatoMedico(String codCertificatoMedico);
}
