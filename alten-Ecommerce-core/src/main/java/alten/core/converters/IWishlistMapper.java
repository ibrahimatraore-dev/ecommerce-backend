package alten.core.converters;


import alten.core.dtos.product.ProductRequestDTO;
import alten.core.dtos.product.ProductResponseDTO;
import alten.core.dtos.wishlist.WishlistItemDTO;
import alten.core.entities.Product;
import alten.core.entities.WishlistItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IWishlistMapper {

    WishlistItemDTO toDTO(WishlistItem wishlistItem);
    WishlistItem toEntity(WishlistItemDTO dto);
}
