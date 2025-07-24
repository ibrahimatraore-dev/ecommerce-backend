package alten.api.config.filter;

import alten.api.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Filtre d'authentification JWT exécuté pour chaque requête HTTP.
 *
 * - Ignore les routes /users/account et /token (pas besoin de token).
 * - Récupère le token JWT depuis l'en-tête "Authorization".
 * - Extrait le nom d'utilisateur à partir du token.
 * - Vérifie si le token est valide et, si oui, authentifie l'utilisateur dans le contexte de sécurité Spring.
 */
@Component
@Slf4j
@AllArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectProvider<UserDetailsService> userDetailsServiceProvider;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String path = request.getRequestURI();
        String contextPath = request.getContextPath();
        String uri = path.substring(contextPath.length());
        String username = null;

        if (
                uri.equals("/users/account") ||
                        uri.equals("/token") ||
                        uri.equals("/products/all")
        )
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");


        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            username = jwtTokenUtil.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetailsService userDetailsService = userDetailsServiceProvider.getIfAvailable();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}

