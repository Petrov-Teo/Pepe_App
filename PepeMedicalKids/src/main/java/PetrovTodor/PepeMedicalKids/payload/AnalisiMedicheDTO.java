package PetrovTodor.PepeMedicalKids.payload;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.AnalisiRegionali;
import PetrovTodor.PepeMedicalKids.enums.TipoPrescrizione;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AnalisiMedicheDTO(
        @NotBlank(message = "Il Campo note è obbligatorio!")
        String note,
        @NotBlank(message = "Il Campo tipoPrescrizione è obbligatorio!")
        TipoPrescrizione tipoPrescrizione,
        @NotBlank(message = "Il Camo analisiRegionali è obbligatorio!")
        List<AnalisiRegionali> analisiRegionali
) {
}