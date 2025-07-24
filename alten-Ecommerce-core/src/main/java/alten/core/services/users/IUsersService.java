package alten.core.services.users;

import alten.core.dtos.auth.AuthUserDetailsDTO;
import alten.core.dtos.user.UserDTO;
import alten.core.dtos.user.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Interface of user service.
 */
public interface IUsersService {

    /**
     * Get current user
     *
     * @return current user.
     */
    UserDTO getCurrentUser();

    /**
     * Find a user by email
     *
     * @param email email
     * @return User DTO.
     */
    Optional<UserDTO> findByEmail(final String email);

    /**
     * Check if current user is an admin
     *
     * @return boolean
     */
    Boolean isCurrentUserAdmin();



    Optional<AuthUserDetailsDTO> findAuthByEmail(final String email);

    /**
     * Register a new user in the system.
     *
     * @param userUpdateDTO DTO containing the user's registration details
     * @return The newly registered user as a DTO
     */
    UserDTO registerUser(UserUpdateDTO userUpdateDTO);

    /**
     * Update an existing user with new information.
     *
     * @param id            the ID of the user to update
     * @param userUpdateDTO DTO containing updated user information
     * @return The updated user as a DTO
     */
    UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);

    /**
     * Get a user by their ID.
     *

     * @return the user as a DTO
     */
    UserDTO getUser();
    /**
     * Check if current user has admin email
     *
     * @return true if current user email is admin@admin.com
     */
    boolean isCurrentUserAdminEmail();
}
