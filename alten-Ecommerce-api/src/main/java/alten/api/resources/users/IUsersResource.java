package alten.api.resources.users;

import alten.core.dtos.user.UserDTO;
import alten.core.dtos.user.UserUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface IUsersResource {


    /**
     * Register new user
     * @param userUpdateDTO user data
     * @return created user
     */
    @Operation(summary = "Register new user")
    ResponseEntity<UserDTO> register(UserUpdateDTO userUpdateDTO);

    /**
     * Update user information
     * @param id user ID
     * @param userUpdateDTO updated data
     * @return updated user
     */
    @Operation(summary = "Update user information")
    ResponseEntity<UserDTO> updateUser(Long id, UserUpdateDTO userUpdateDTO);

    /**
     * Get user by ID
     * @return user information
     */
    @Operation(summary = "Get user ")
    ResponseEntity<UserDTO> getUser();

}
