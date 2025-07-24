package alten.core.entities;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistItem {
    private Long id;
    private Product product;
    private User user;
}
