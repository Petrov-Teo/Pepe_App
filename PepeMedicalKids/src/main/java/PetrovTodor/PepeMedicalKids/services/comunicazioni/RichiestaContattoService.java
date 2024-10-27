package PetrovTodor.PepeMedicalKids.services.comunicazioni;

import PetrovTodor.PepeMedicalKids.entities.cominicazioni.RichiestaContatto;
import PetrovTodor.PepeMedicalKids.exceptions.BadRequestException;
import PetrovTodor.PepeMedicalKids.exceptions.NotFoundException;
import PetrovTodor.PepeMedicalKids.payload.comunicazioni.RichiestaContattoDTO;
import PetrovTodor.PepeMedicalKids.repositorys.comunicazioni.RichiestaContattoRepository;
import PetrovTodor.PepeMedicalKids.services.emeil.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class RichiestaContattoService {
    @Autowired
    private RichiestaContattoRepository richiestaContattoRepository;
    @Autowired
    private EmailService emailService;


    public Page<RichiestaContatto> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.richiestaContattoRepository.findAll(pageable);
    }


    public RichiestaContatto saveRichiestaContatto(RichiestaContattoDTO body) throws MessagingException {
        if (body.nome() == null || body.email() == null || body.note() == null) {
            throw new BadRequestException("Tutti i campi sono obbligatori.");
        }

        RichiestaContatto richiestaContatto = new RichiestaContatto(body.nome(), body.email(), body.note());
        emailService.sendContactResponse(body.email(), body.nome());
        return this.richiestaContattoRepository.save(richiestaContatto);
    }

    public RichiestaContatto findByIdRichiestaContatto(UUID idRichiestaContatto) {
        RichiestaContatto richiestaContatto = this.richiestaContattoRepository
                .findById(idRichiestaContatto)
                .orElseThrow(()
                        -> new NotFoundException(idRichiestaContatto));
        richiestaContatto.changeLetto();
        return this.richiestaContattoRepository.save(richiestaContatto);
    }

    public RichiestaContatto findByName(String nome) {
        return this.richiestaContattoRepository
                .findByNome(nome)
                .orElseThrow(()
                        -> new NotFoundException(nome));

    }

    public RichiestaContatto findByEmail(String email) {
        return this.richiestaContattoRepository
                .findByEmail(email)
                .orElseThrow(()
                        -> new NotFoundException(email));

    }


    public RichiestaContatto findAndUpdate(UUID idRichiestaContatto, RichiestaContattoDTO body) {
        RichiestaContatto richiestaContatto = findByIdRichiestaContatto(idRichiestaContatto);
        richiestaContatto.setNome(body.nome());
        richiestaContatto.setEmail(body.email());
        richiestaContatto.setNote(body.note());
        return this.richiestaContattoRepository.save(richiestaContatto);
    }

    public void findAndDelete(UUID idRichiestaContatto) {
        RichiestaContatto richiestaContatto = findByIdRichiestaContatto(idRichiestaContatto);
        this.richiestaContattoRepository.delete(richiestaContatto);

    }

}
