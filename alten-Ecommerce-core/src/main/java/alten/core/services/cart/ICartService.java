package alten.core.services.cart;

import alten.core.dtos.cart.CartItemDTO;

import java.util.List;

/**
 * Service interface for handling cart operations.
 * Provides business logic for managing items in a user's cart.
 */
public interface ICartService {

    /**
     * Add a product to the current user's cart.
     *
     * @param productId ID of the product to add
     * @param quantity quantity of the product
     */
    void addToCart(Long productId, int quantity);

    /**
     * Remove a product from the current user's cart.
     *
     * @param productId ID of the product to remove
     */
    void removeFromCart(Long productId);

    /**
     * Get the current user's cart with all items.
     *
     * @return list of cart items
     */
    List<CartItemDTO> getCart();

    /**
     * Update the quantity of a specific product in the user's cart.
     *
     * @param productId ID of the product
     * @param quantity new quantity to set
     */
    void updateCartItemQuantity(Long productId, int quantity);
}
