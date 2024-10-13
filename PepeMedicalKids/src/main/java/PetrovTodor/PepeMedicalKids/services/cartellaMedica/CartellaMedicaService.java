package PetrovTodor.PepeMedicalKids.services.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.CartellaMedica;
import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.cartellaMedica.CartellaMedicaDTO;
import PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica.CartellaMedicaRepository;
import PetrovTodor.PepeMedicalKids.services.users.MedicoService;
import PetrovTodor.PepeMedicalKids.services.users.PazienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartellaMedicaService {
    @Autowired
    private CartellaMedicaRepository cartellaMedicaRepository;
    @Autowired
    private PazienteService pazienteRepository;
    @Autowired
    private MedicoService medicoService;

//SAVE

    public CartellaMedica save(CartellaMedicaDTO body) {
        CartellaMedica cartellaMedica = new CartellaMedica(body.paziente());
        return this.cartellaMedicaRepository.save(cartellaMedica);
    }

    //FIND BY ID
    public CartellaMedica findById(UUID idCartella) {
        return this.cartellaMedicaRepository.findById(idCartella).
                orElseThrow(()
                        -> new NotFoundException("Nessuna Cartella Medica Trovata con : " + idCartella));
    }

    //FIND BY PAZIENTE
    public CartellaMedica findCartellaMedicaByPaziente(String codPaziente) {
        return this.cartellaMedicaRepository.findCartellaMedicaByPaziente_CodPaziente(codPaziente).
                orElseThrow(()
                        -> new NotFoundException("Nessuna Cartella Medica trova per il paziente: " + codPaziente + "!"));
    }

    //ADD MEDICO TO CARTELLA MEDICA
    public CartellaMedica addMediciToCartellaMedica(String codMedico, String codCartella) {
        CartellaMedica foundCartellaMedica = this.cartellaMedicaRepository.
                findCartellaMedicaByNumeroCartella(codCartella).orElseThrow(()
                        -> new NotFoundException("Nessuna Cartella Medica Trovata con il :" + codCartella + "!"));
        Medico foundMedico = this.medicoService.findMedicoByCodMedico(codMedico);
        List<Medico> medici = foundCartellaMedica.getMedici();
        if (!medici.contains(foundMedico)) {
            medici.add(foundMedico);
        }
        foundCartellaMedica.setMedici(medici);

        this.cartellaMedicaRepository.save(foundCartellaMedica);

        return foundCartellaMedica;
    }

    public CartellaMedica removeMediciToCartellaMedica(String codMedico, String codCartella) {
        CartellaMedica foundCartellaMedica = this.cartellaMedicaRepository.
                findCartellaMedicaByNumeroCartella(codCartella).orElseThrow(()
                        -> new NotFoundException("Nessuna Cartella Medica Trovata con il :" + codCartella + "!"));
        Medico foundMedico = this.medicoService.findMedicoByCodMedico(codMedico);
        List<Medico> medici = foundCartellaMedica.getMedici();
        if (medici.contains(foundMedico)) {
            medici.remove(foundMedico);
        } else {
            throw new NotFoundException("Il Medico con il codice: " + codMedico + " non è associato alla cartella medica!");
        }
        foundCartellaMedica.setMedici(medici);

        this.cartellaMedicaRepository.save(foundCartellaMedica);

        return foundCartellaMedica;
    }


    public CartellaMedica findAndUpdate(UUID idCartella, CartellaMedicaDTO body) {
        CartellaMedica found = this.findById(idCartella);
        found.setPaziente(body.paziente());
        return this.cartellaMedicaRepository.save(found);
    }

    public void fondeAndDelete(UUID idCartella) {
        CartellaMedica found = this.findById(idCartella);
        this.cartellaMedicaRepository.delete(found);
    }
}
