package alten.core.dtos.auth;

import alten.core.entities.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthUserDetailsDTO {

    private String emailAddress;
    private String password;
    private UserRole role;
}
