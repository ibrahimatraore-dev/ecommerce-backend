package alten.core.ports.datasources;


import alten.core.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserPort {

    Optional<User> findByEmail(String email);

    /**
     * Saves or updates a user entity.
     *
     * @param user the user to save
     * @return the saved user, with updated ID if applicable
     */
    User save(User user);

    /**
     * Finds a user by their unique ID.
     *
     * @param id the ID of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    Optional<User> findById(Long id);

    /**
     * Deletes a user by their unique ID.
     *
     * @param id the ID of the user to delete
     */
    void deleteById(Long id);
}
