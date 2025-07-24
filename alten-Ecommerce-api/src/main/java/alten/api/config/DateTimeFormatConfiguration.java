package alten.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration globale du formatage des dates dans l'application.
 *
 * Cette classe permet de forcer l’utilisation du format ISO (ex: 2025-07-24T14:36:30Z)
 * pour les dates et heures dans les requêtes et réponses HTTP (JSON).
 */
@Configuration
public class DateTimeFormatConfiguration implements WebMvcConfigurer {

    /**
     * Enregistre un formateur de date pour utiliser le format ISO.
     * Cela évite d'avoir à utiliser @DateTimeFormat à chaque fois.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }
}