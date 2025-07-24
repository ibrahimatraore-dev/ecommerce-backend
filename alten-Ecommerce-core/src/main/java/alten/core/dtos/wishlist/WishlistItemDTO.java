package alten.core.dtos.wishlist;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItemDTO {
    private Long id;
    private Long productID;
}