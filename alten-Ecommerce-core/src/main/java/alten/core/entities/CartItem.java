package alten.core.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private Long id;
    private Product product;
    private User user;
    private int quantity;
}