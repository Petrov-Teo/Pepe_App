package PetrovTodor.PepeMedicalKids.configuration.pdf;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DropboxUploader {
    private final DropboxConfig dropboxConfig;

    @Autowired
    public DropboxUploader(DropboxConfig dropboxConfig) {
        this.dropboxConfig = dropboxConfig;
    }

    public void uploadFileToDropbox(String pdfPath) {
        // Configurazione del client Dropbox
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, dropboxConfig.getAccessToken());

        try (InputStream inputStream = new FileInputStream(pdfPath)) {
            // Ottieni il nome del file usando la classe File di Java
            File file = new File(pdfPath);
            String fileName = file.getName();

            // Carica il file su Dropbox
            FileMetadata metadata = client.files().uploadBuilder("/fatture/" + fileName)
                    .withMode(WriteMode.OVERWRITE) // Sovrascrivi se esiste già
                    .uploadAndFinish(inputStream);

            System.out.println("File caricato su Dropbox: " + metadata.getPathLower());
        } catch (IOException | com.dropbox.core.DbxException e) {
            e.printStackTrace();
        }
    }
}