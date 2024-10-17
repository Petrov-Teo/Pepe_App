package PetrovTodor.PepeMedicalKids.controllers.serviziPrenotabili;

import PetrovTodor.PepeMedicalKids.entities.serviziPrenotabili.VisitaPrenotabile;
import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.payload.serviziPrenotabili.VisitaPrenotabileDTO;
import PetrovTodor.PepeMedicalKids.services.serviziPrenotabili.VisitaPrenotabileService;
import PetrovTodor.PepeMedicalKids.services.users.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/visite-prenotabili")
public class VisitaPrenotabileController {

    @Autowired
    private VisitaPrenotabileService visitaPrenotabileService;
    @Autowired
    private MedicoService medicoService;

    // Trova tutte le visite prenotabili
    @GetMapping
    public ResponseEntity<Page<VisitaPrenotabile>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "tipo") String sortBy) {
        Page<VisitaPrenotabile> visite = visitaPrenotabileService.findAll(page, size, sortBy);
        return ResponseEntity.ok(visite);
    }

    // Trova visita prenotabile per ID
    @GetMapping("/{id}")
    public ResponseEntity<VisitaPrenotabile> findById(@PathVariable UUID id) {
        VisitaPrenotabile visita = visitaPrenotabileService.findByIdVisitaPrenotabile(id);
        return ResponseEntity.ok(visita);
    }

    // Crea una nuova visita prenotabile
    @PostMapping
    public ResponseEntity<VisitaPrenotabile> create(@RequestBody VisitaPrenotabileDTO visitaDTO) {
        VisitaPrenotabile nuovaVisita = visitaPrenotabileService.saveVisitaPrenotabile(visitaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovaVisita);
    }

    // Aggiorna una visita prenotabile esistente
    @PutMapping("/{id}")
    public ResponseEntity<VisitaPrenotabile> update(@PathVariable UUID id, @RequestBody VisitaPrenotabileDTO visitaDTO) {
        VisitaPrenotabile visitaAggiornata = visitaPrenotabileService.findAndUpdate(id, visitaDTO);
        return ResponseEntity.ok(visitaAggiornata);
    }

    // Cancella una visita prenotabile
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        visitaPrenotabileService.findAndDelete(id);
        return ResponseEntity.noContent().build(); // Restituisce 204 No Content
    }

    // Trova visita prenotabile per tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<VisitaPrenotabile> findByTipo(@PathVariable String tipo) {
        VisitaPrenotabile visita = visitaPrenotabileService.findByTipoVisita(tipo);
        return ResponseEntity.ok(visita);
    }

    // Trova visita prenotabile per descrizione
    @GetMapping("/descrizione/{descrizione}")
    public ResponseEntity<VisitaPrenotabile> findByDescrizione(@PathVariable String descrizione) {
        VisitaPrenotabile visita = visitaPrenotabileService.findByDescrizione(descrizione);
        return ResponseEntity.ok(visita);
    }

    // Trova visita prenotabile per medico
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<VisitaPrenotabile> findByMedico(@PathVariable UUID medicoId) {
        Medico medico = medicoService.findMedicoByIdMedico(medicoId); // Assicurati che questa chiamata sia disponibile nel tuo MedicoService
        VisitaPrenotabile visita = visitaPrenotabileService.findByDescrizione(medico);
        return ResponseEntity.ok(visita);
    }
}