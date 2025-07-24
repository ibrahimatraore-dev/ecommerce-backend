package alten.core.dtos.user;

import alten.core.dtos.BaseEntityDTO;
import alten.core.entities.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO extends BaseEntityDTO {
    private Long id;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private UserRole role;

}
