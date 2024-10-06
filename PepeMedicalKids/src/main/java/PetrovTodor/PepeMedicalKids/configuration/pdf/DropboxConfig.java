package PetrovTodor.PepeMedicalKids.configuration.pdf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class DropboxConfig {

    @Value("${dropbox.api.key}")
    private String accessToken;

    @Value("${dropbox.api.secret}")
    private String secretToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getSecretToken() {
        return secretToken;
    }
}