package alten.api.resources.cart;

import alten.core.dtos.cart.CartItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICartResource {

    /**
     * Get current user's cart
     * @return list of CartItemDTO
     */
    @Operation(summary = "Get current user's cart")
    ResponseEntity<List<CartItemDTO>> getCart();

    /**
     * Add product to cart
     * @param productId id of the product
     * @param quantity quantity to add
     */
    @Operation(summary = "Add product to cart")
    ResponseEntity<Void> addToCart(Long productId, int quantity);

    /**
     * Remove product from cart
     * @param productId id of the product to remove
     */
    @Operation(summary = "Remove product from cart")
    ResponseEntity<Void> removeFromCart(Long productId);

    /**
     * Update quantity of a product in the cart
     * @param productId id of the product
     * @param quantity new quantity
     */
    @Operation(summary = "Update quantity of a product in the cart")
    ResponseEntity<Void> updateQuantity(Long productId, int quantity);
}