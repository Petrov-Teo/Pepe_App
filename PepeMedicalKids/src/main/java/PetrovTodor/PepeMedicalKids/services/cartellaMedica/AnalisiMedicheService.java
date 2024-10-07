package PetrovTodor.PepeMedicalKids.services.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.AnalisiMediche;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.AnalisiMedicheDTO;
import PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica.AnalisiMedicheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AnalisiMedicheService {
    @Autowired
    private AnalisiMedicheRepository analisiMedicheRepository;


    //CRUD


    public Optional<AnalisiMediche> findByCodAnalisi(String codAnalisi) {
        Optional<AnalisiMediche> analisiMedicheTrovate = this.analisiMedicheRepository.
                findAnalisiMedicheByCodAnalisi(codAnalisi);
        if (analisiMedicheTrovate.isEmpty()) {
            throw new NotFoundException("Analisi" + codAnalisi + " non trovati!");
        }
        return analisiMedicheTrovate;
    }

    public AnalisiMediche findById(UUID idAnalisi) {
        return this.analisiMedicheRepository.findById(idAnalisi).
                orElseThrow(() -> new NotFoundException("Analisi Mediche con " + idAnalisi + " non trovati!"));
    }

    public AnalisiMediche save(AnalisiMedicheDTO body) {
        String ultimoCodice = analisiMedicheRepository.findMaxCodAdmin();

        if (ultimoCodice != null) {
            String parteNumerica = ultimoCodice.substring(1);
            ultimoCodice = String.valueOf(Integer.parseInt(parteNumerica) + 1);
        }

        AnalisiMediche analisiMediche = new AnalisiMediche(
                body.tipoPrescrizione(),
                body.note(),
                body.analisiRegionali());
        analisiMediche.setNumCertificato(ultimoCodice);
        return this.analisiMedicheRepository.save(analisiMediche);
    }

    public AnalisiMediche findAndUpdate(UUID idAnalisiMediche, AnalisiMedicheDTO body) {
        AnalisiMediche found = findById(idAnalisiMediche);
        found.setTipoPrescrizione(body.tipoPrescrizione());
        found.setNote(body.note());
        found.setAnalisiRegionali(body.analisiRegionali());
        return this.analisiMedicheRepository.save(found);
    }

    public void findAndDelete(UUID idAnalisi) {
        AnalisiMediche found = findById(idAnalisi);
        this.analisiMedicheRepository.delete(found);
    }
}
