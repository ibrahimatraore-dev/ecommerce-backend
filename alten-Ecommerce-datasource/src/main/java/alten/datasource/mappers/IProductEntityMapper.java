package alten.datasource.mappers;

import alten.core.entities.Product;
import alten.datasource.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IProductEntityMapper {
    ProductEntity toEntity(Product product);
    Product toDomain(ProductEntity entity);
}
