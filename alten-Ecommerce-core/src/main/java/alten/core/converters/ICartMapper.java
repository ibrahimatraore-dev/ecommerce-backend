package alten.core.converters;


import alten.core.dtos.cart.CartItemDTO;
import alten.core.dtos.product.ProductRequestDTO;
import alten.core.dtos.product.ProductResponseDTO;
import alten.core.entities.CartItem;
import alten.core.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICartMapper {

    CartItemDTO toDTO(CartItem cartItem);
    CartItem toEntity(CartItemDTO dto);
}
