package PetrovTodor.PepeMedicalKids.services.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.AnalisiRegionali;
import PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica.AnalisiRegionaliRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class AnalisiRegionaliService {
    @Autowired
    private AnalisiRegionaliRepository analisiRegionaliRepository;

    public void caricaDatiDaCSV() {
        try {

            ClassPathResource resource = new ClassPathResource("csv/analisi.csv"); // percorso del file


            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            CSVReader csvReader = new CSVReader(reader);

            String[] nextLine;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            boolean isFirstLine = true;
            while ((nextLine = csvReader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;  // Ignora la prima riga
                    continue;
                }

                AnalisiRegionali analisi = new AnalisiRegionali();
                analisi.setIdEsame(UUID.randomUUID());
                analisi.setCodiceNtrRl(nextLine[0]);
                analisi.setDescrizioneCodiceNtrlRl(nextLine[1]);
                analisi.setDescrizionePadre(nextLine[2]);
                analisi.setDescrizioneFigli(nextLine[3]);
                analisi.setNumeroProgressivo(nextLine[4]);
                analisi.setCdiceCUR(nextLine[5]);
                analisi.setCompatibilita(nextLine[6].charAt(0));
                analisi.setPeso(Integer.parseInt(nextLine[7]));
                analisi.setBranca_1(Integer.parseInt(nextLine[8]));
                analisi.setBranca_2(Integer.parseInt(nextLine[9]));
                analisi.setBranca_3(Integer.parseInt(nextLine[10]));
                analisi.setBranca_4(Integer.parseInt(nextLine[11]));
                analisi.setIncompatibilitaConAltriCodici(nextLine[12]);
                analisi.setInclusi(nextLine[13]);
                analisi.setNoteEcondizioniDiErgabilitaParticolari(nextLine[14]);
                analisi.setNSeduteCiclo(nextLine[15]);
                analisi.setTariffaEuro(Double.parseDouble(nextLine[16]));
                analisi.setStampa(nextLine[17]);
                analisi.setNote(nextLine[18]);
                analisi.setDataFine(LocalDate.parse(nextLine[19], formatter));


                analisiRegionaliRepository.save(analisi);
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}

