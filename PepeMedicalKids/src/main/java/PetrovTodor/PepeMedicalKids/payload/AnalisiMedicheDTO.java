package PetrovTodor.PepeMedicalKids.payload;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.AnalisiRegionali;
import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AnalisiMedicheDTO(
        @NotEmpty(message = "Il Campo note è obbligatorio!")
        String note,
        @NotEmpty(message = "Il Campo tipoPrescrizione è obbligatorio!")
        TipoPrescrizione tipoPrescrizione,
        @NotEmpty(message = "Il Camo analisiRegionali è obbligatorio!")
        List<AnalisiRegionali> analisiRegionali
) {
}