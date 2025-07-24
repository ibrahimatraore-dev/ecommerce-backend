package alten.core.ports.datasources;

import alten.core.entities.CartItem;
import alten.core.entities.Product;
import alten.core.entities.User;
import alten.core.dtos.cart.CartItemDTO;

import java.util.List;

/**
 * Data access interface for Cart operations.
 * Handles persistence and retrieval of cart-related data.
 */
public interface ICartPort {

    /**
     * Save or update a CartItem in the data source.
     *
     * @param item the CartItem to save
     * @return the saved CartItem
     */
    CartItem save(CartItem item);

    /**
     * Delete a CartItem by user ID and product ID.
     *
     * @param userId ID of the user
     * @param productId ID of the product to remove
     */
    void deleteByUserIdAndProductId(Long userId, Long productId);

    /**
     * Retrieve all CartItems for a specific user.
     *
     * @param userId ID of the user
     * @return list of CartItems
     */
    List<CartItem> findByUserId(Long userId);



    /**
     * Add a product to the user's cart.
     *
     * @param user the user
     * @param product the product to add
     * @param quantity quantity to add
     */
    void addToCart(User user, Product product, int quantity);

    /**
     * Remove a product from the user's cart.
     *
     * @param user the user
     * @param productId ID of the product to remove
     */
    void removeFromCart(User user, Long productId);

    /**
     * Update the quantity of a product in the user's cart.
     *
     * @param user the user
     * @param productId ID of the product to update
     * @param quantity new quantity to set
     */
    void updateCartQuantity(User user, Long productId, int quantity);

    /**
     * Get the user's full cart as a list of DTOs.
     *
     * @param user the user
     * @return list of CartItemDTOs
     */
    List<CartItemDTO> getCart(User user);
}