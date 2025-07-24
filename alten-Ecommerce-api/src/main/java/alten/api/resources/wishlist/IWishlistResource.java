package alten.api.resources.wishlist;

import alten.core.dtos.wishlist.WishlistItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IWishlistResource {

    /**
     * Get current user's wishlist
     * @return list of WishlistItemDTO
     */
    @Operation(summary = "Get current user's wishlist")
    ResponseEntity<List<WishlistItemDTO>> getWishlist();

    /**
     * Add product to wishlist
     * @param productId id of the product to add
     */
    @Operation(summary = "Add product to wishlist")
    ResponseEntity<Void> addToWishlist(Long productId);

    /**
     * Remove product from wishlist
     * @param productId id of the product to remove
     */
    @Operation(summary = "Remove product from wishlist")
    ResponseEntity<Void> removeFromWishlist(Long productId);
}