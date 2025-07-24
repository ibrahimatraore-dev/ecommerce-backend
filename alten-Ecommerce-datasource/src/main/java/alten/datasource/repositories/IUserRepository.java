package alten.datasource.repositories;

import alten.datasource.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for managing UserEntity persistence operations.
 */
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Find a user by email address.
     *
     * @param email the email address of the user
     * @return an Optional containing the UserEntity if found, or empty if not
     */
    @Query("SELECT u FROM UserEntity u WHERE u.emailAddress = ?1")
    Optional<UserEntity> findByEmail(String email);
}