package alten.datasource.repositories;

import alten.datasource.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItemEntity, Long> {

    /**
     * Retrieve all cart items for a specific user by their ID.
     *
     * @param userId the ID of the user
     * @return list of cart items
     */
    List<CartItemEntity> findByUserId(Long userId);

    /**
     * Find a cart item by user ID and product ID (used to check if product is already in cart).
     *
     * @param userId the ID of the user
     * @param productId the ID of the product
     * @return optional cart item
     */
    Optional<CartItemEntity> findByUserIdAndProductId(Long userId, Long productId);

    /**
     * Delete a specific cart item using both user ID and product ID.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product
     */
    void deleteByUserIdAndProductId(Long userId, Long productId);
}
