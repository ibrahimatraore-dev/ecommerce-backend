package alten.core.entities;

import alten.core.entities.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseEntity {

    private Long id;
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;


}
