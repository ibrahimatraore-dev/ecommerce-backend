package alten.core;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties()
@Configuration
@Data
@Setter
@Getter
public class ApplicationProperties {

    @Value("${application.version}")
    private String version;

    @Value("${application.env}")
    private String environnement;

    @Value("${application.id_client}")
    private String idClient;

    @Value("${application.api_key}")
    private String apiKey;

    @Value("${application.jwt_secret}")
    private String jwtSecret;

}
