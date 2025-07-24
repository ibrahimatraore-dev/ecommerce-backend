package alten.api.resources.auth;

import alten.api.utils.JwtTokenUtil;
import alten.core.dtos.auth.AuthTokenDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
@Slf4j
public class AuthResourceImpl implements IAuthResource{

    private final AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Override
    @PostMapping("")
    public ResponseEntity<AuthTokenDTO> login(@Valid @RequestBody AuthRequestBody body) {
        log.info("[API : login : BEGIN]");
        try{
            AuthTokenDTO tokenDTO = this.login(body.getEmailAddress(), body.getPassword());
            log.info("[API : login : END]");
            return ResponseEntity.ok(tokenDTO);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private AuthTokenDTO login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        UserDetails user = userDetailsService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(user);
        return new AuthTokenDTO(token);
    }
}
