package alten.core.converters;


import alten.core.dtos.product.ProductRequestDTO;
import alten.core.dtos.product.ProductResponseDTO;
import alten.core.entities.Product;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductMapper {

    ProductRequestDTO toDTO(Product product);
    Product toEntity(ProductRequestDTO dto);
    Product fromResponse(ProductResponseDTO responseDTO);
    ProductResponseDTO toResponseDTO(Product product);
}
