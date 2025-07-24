package alten.core.services.wishlist;

import alten.core.dtos.wishlist.WishlistItemDTO;
import java.util.List;

/**
 * Service interface for managing the user's wishlist.
 */
public interface IWishlistService {

    /**
     * Add a product to the current user's wishlist.
     *
     * @param productId the ID of the product to add
     */
    void addToWishlist(Long productId);

    /**
     * Remove a product from the current user's wishlist.
     *
     * @param productId the ID of the product to remove
     */
    void removeFromWishlist(Long productId);

    /**
     * Retrieve the current user's wishlist items.
     *
     * @return a list of WishlistItemDTO representing the wishlist
     */
    List<WishlistItemDTO> getWishlist();
}
