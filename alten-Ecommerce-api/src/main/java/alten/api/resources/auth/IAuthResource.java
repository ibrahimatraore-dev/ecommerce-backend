package alten.api.resources.auth;

import alten.core.dtos.auth.AuthTokenDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthResource {

    /**
     * login with email and password.
     * @param body the authentication request body
     * @return the response entity
     **/
    ResponseEntity<AuthTokenDTO> login(AuthRequestBody body);
}