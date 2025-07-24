package alten.datasource.repositories;

import alten.datasource.entities.WishlistItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing WishlistItemEntity data from the database.
 */
@Repository
public interface IWishlistItemRepository extends JpaRepository<WishlistItemEntity, Long> {

   /**
    * Find all wishlist items for a given user ID.
    *
    * @param userId the ID of the user
    * @return list of WishlistItemEntity
    */
   List<WishlistItemEntity> findByUserId(Long userId);

   /**
    * Delete a wishlist item based on user ID and product ID.
    *
    * @param userId the ID of the user
    * @param productId the ID of the product
    */
   void deleteByUserIdAndProductId(Long userId, Long productId);
}