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
                jacksonConverter.setPrettyPrint(false);
            }
        }
    }

    /**
     * Redirections spécifiques pour que Swagger UI fonctionne correctement
     * via les routes /api/**.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/api/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
    }

    /**
     * Définit où trouver les ressources Swagger (HTML, JS, CSS) côté frontend.
     * Cela permet de charger correctement l'interface Swagger UI via les routes /api/**.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/api/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}