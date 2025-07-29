package alten.api.resources.wishlist;

import alten.core.dtos.wishlist.WishlistItemDTO;
import alten.core.services.wishlist.IWishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Wishlist API")
@AllArgsConstructor
@Slf4j
public class WishlistResourceImpl implements IWishlistResource {

    private final IWishlistService wishlistService;

    @Operation(summary = "Get current user's wishlist")
    @GetMapping("")
    public ResponseEntity<List<WishlistItemDTO>> getWishlist() {
        log.info("[API : getWishlist : BEGIN]");
        List<WishlistItemDTO> wishlist = wishlistService.getWishlist();
        log.info("[API : getWishlist : END]");
        return ResponseEntity.ok(wishlist);
    }

    @Operation(summary = "Add product to wishlist")
    @PostMapping("/add")
    public ResponseEntity<Void> addToWishlist(@RequestParam Long productId) {
        log.info("[API : addToWishlist : BEGIN]");
        wishlistService.addToWishlist(productId);
        log.info("[API : addToWishlist : END]");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove product from wishlist")
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromWishlist(@RequestParam Long productId) {
        log.info("[API : removeFromWishlist : BEGIN]");
        wishlistService.removeFromWishlist(productId);
        log.info("[API : removeFromWishlist : END]");
        return ResponseEntity.ok().build();
    }
}