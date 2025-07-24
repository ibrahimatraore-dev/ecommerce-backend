package alten.core.ports.datasources;

import alten.core.entities.WishlistItem;
import java.util.List;

/**
 * Data access interface for Wishlist operations.
 * Handles persistence and retrieval of wishlist-related data.
 */
public interface IWishlistPort {

    /**
     * Save a wishlist item to the data source.
     *
     * @param item the WishlistItem to save
     * @return the saved WishlistItem
     */
    WishlistItem save(WishlistItem item);

    /**
     * Delete a wishlist item by user ID and product ID.
     *
     * @param userId ID of the user
     * @param productId ID of the product to remove from the wishlist
     */
    void deleteByUserIdAndProductId(Long userId, Long productId);

    /**
     * Retrieve all wishlist items for a specific user.
     *
     * @param userId ID of the user
     * @return list of WishlistItems
     */
    List<WishlistItem> findByUserId(Long userId);
}