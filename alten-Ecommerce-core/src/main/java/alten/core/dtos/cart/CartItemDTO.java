package alten.core.dtos.cart;


import alten.core.dtos.product.ProductRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private Long productID;
    private int quantity;
}
