package alten.core.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Auth token dto.
 */
@AllArgsConstructor
@Getter
@Setter
@Data
public class AuthTokenDTO {
    private String token;
}
