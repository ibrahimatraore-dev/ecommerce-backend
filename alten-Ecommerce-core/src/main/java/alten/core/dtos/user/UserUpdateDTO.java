package alten.core.dtos.user;

import alten.core.entities.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {
    @NotBlank
    @Email
    private String emailAddress;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String password;

    private UserRole role;

}