package alten.core.services.products;

import alten.core.dtos.product.ProductRequestDTO;
import alten.core.dtos.product.ProductResponseDTO;
import alten.core.entities.Product;
import alten.core.exceptions.ResourceNotFoundException;
import alten.core.ports.datasources.IProductPort;
import alten.core.converters.IProductMapper;
import alten.core.services.users.IUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductPort productPort;
    private final IProductMapper productMapper;
    private final IUsersService usersService;

    @Override
    public ProductResponseDTO create(ProductRequestDTO dto) {
        log.info("Attempting to create product with name: {}", dto.getName());
        checkIfCurrentUserIsAdminEmail();
        Product entity = productMapper.toEntity(dto);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        Product saved = productPort.save(entity);
        log.info("Product created successfully with ID: {}", saved.getId());
        return productMapper.toResponseDTO(saved);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        log.info("Attempting to update product with ID: {}", id);
        checkIfCurrentUserIsAdminEmail();
        Product existing = productPort.findById(id)
                .orElseThrow(() -> {
                    log.error("Update failed: product not found with ID {}", id);
                    return new ResourceNotFoundException("Produit introuvable");
                });
        Product updated = productMapper.toEntity(dto);
        updated.setId(id);
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setUpdatedAt(Instant.now());
        Product saved = productPort.save(updated);
        log.info("Product with ID {} updated successfully", id);
        return productMapper.toResponseDTO(saved);
    }

    @Override
    public void delete(Long id) {
        log.info("Attempting to delete product with ID: {}", id);
        checkIfCurrentUserIsAdminEmail();
        productPort.deleteById(id);
        log.info("Product with ID {} deleted successfully", id);
    }

    @Override
    public Page<ProductResponseDTO> list(String keyword, Pageable pageable) {
        log.info("Fetching products with keyword: {}", keyword != null ? keyword : "[none]");
        return productPort.findAll(keyword, pageable).map(productMapper::toResponseDTO);
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        log.info("Fetching product with ID: {}", id);
        return productPort.findById(id)
                .map(productMapper::toResponseDTO)
                .orElseThrow(() -> {
                    log.error("Product not found with ID {}", id);
                    return new ResourceNotFoundException("Produit introuvable");
                });
    }

    @Override
    public ProductResponseDTO updateQuantity(Long id, int quantity) {
        log.info("Updating quantity for product ID: {} to {}", id, quantity);
        checkIfCurrentUserIsAdminEmail();
        Product product = productPort.findById(id)
                .orElseThrow(() -> {
                    log.error("Update quantity failed: product not found with ID {}", id);
                    return new ResourceNotFoundException("Produit introuvable");
                });
        product.setQuantity(quantity);
        product.setUpdatedAt(Instant.now());
        Product saved = productPort.save(product);
        log.info("Quantity for product ID {} updated to {}", id, quantity);
        return productMapper.toResponseDTO(saved);
    }

    private void checkIfCurrentUserIsAdminEmail() {
        if (!usersService.isCurrentUserAdminEmail()) {
            log.warn("Unauthorized access attempt: current user is not admin@admin.com");
            throw new AccessDeniedException("Accès refusé : seul admin@admin.com est autorisé");
        }
    }
}