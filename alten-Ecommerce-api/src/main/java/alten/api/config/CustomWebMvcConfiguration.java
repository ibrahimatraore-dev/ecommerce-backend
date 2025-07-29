package alten.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configuration personnalisée du WebMvc de Spring.
 *
 * - Désactive le formatage JSON "pretty print" (mise en forme avec indentations)
 * - Redirige certaines URLs Swagger pour la documentation API
 * - Ajoute les handlers nécessaires pour servir les ressources statiques Swagger (HTML, JS, etc.)
 */
@Configuration
public class CustomWebMvcConfiguration implements WebMvcConfigurer {

    /**
     * Désactive le "pretty print" pour les réponses JSON.
     * Cela évite que les objets JSON soient formatés avec des sauts de ligne/indentation.
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jacksonConverter) {
                jacksonConverter.setPrettyPrint(true);
            }
        }
    }
}