package alten.datasource.adapters;

import alten.core.entities.Product;
import alten.core.ports.datasources.IProductPort;
import alten.datasource.entities.ProductEntity;
import alten.datasource.mappers.IProductEntityMapper;
import alten.datasource.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductAdapter implements IProductPort {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public Product save(Product product) {
        ProductEntity entity = productEntityMapper.toEntity(product);
        ProductEntity saved = productRepository.save(entity);
        return productEntityMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id)
                .map(productEntityMapper::toDomain);
    }

    @Override
    public Page<Product> findAll(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable)
                    .map(productEntityMapper::toDomain);
        }
        return productRepository.findAll(pageable)
                .map(productEntityMapper::toDomain);
    }
}