package alten.api.resources.cart;

import alten.core.dtos.cart.CartItemDTO;
import alten.core.services.cart.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart API")
@AllArgsConstructor
@Slf4j
public class CartResourceImpl implements ICartResource {

    private final ICartService cartService;

    @Operation(summary = "Get current user's cart")
    @GetMapping("")
    public ResponseEntity<List<CartItemDTO>> getCart() {
        log.info("[API : getCart : BEGIN]");
        List<CartItemDTO> cart = cartService.getCart();
        log.info("[API : getCart : END]");
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Add product to cart")
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        log.info("[API : addToCart : BEGIN]");
        cartService.addToCart(productId, quantity);
        log.info("[API : addToCart : END]");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove product from cart")
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCart(@RequestParam Long productId) {
        log.info("[API : removeFromCart : BEGIN]");
        cartService.removeFromCart(productId);
        log.info("[API : removeFromCart : END]");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update quantity of a product in the cart")
    @PutMapping("/update")
    public ResponseEntity<Void> updateQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        log.info("[API : updateQuantity : BEGIN]");
        cartService.updateCartItemQuantity(productId, quantity);
        log.info("[API : updateQuantity : END]");
        return ResponseEntity.ok().build();
    }
}