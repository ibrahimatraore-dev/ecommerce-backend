package alten.api.config.filter.access;

import alten.core.ApplicationProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@WebFilter(urlPatterns = "/api/*")
@AllArgsConstructor
public class CheckApiKeyFilter extends GenericFilterBean {

    private final ApplicationProperties applicationProperties;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        String headerKey = httpRequest.getHeader("x-app-api-key");
        if (applicationProperties.getApiKey().equals(headerKey)) {
            filterChain.doFilter(request, response);
        } else {
            throw new AccessDeniedException("Mauvaise clé d'API");
        }
    }
}


