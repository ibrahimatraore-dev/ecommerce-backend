package alten.api.resources.products;

import alten.core.dtos.product.ProductRequestDTO;
import alten.core.dtos.product.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IProductResource {

    /**
     * Get paginated list of products, optionally filtered by keyword
     */
    @Operation(summary = "List products with pagination and optional keyword filter")
    ResponseEntity<Page<ProductResponseDTO>> list(String keyword, Pageable pageable);

    /**
     * Get a single product by ID
     */
    @Operation(summary = "Get a product by ID")
    ResponseEntity<ProductResponseDTO> get(Long id);

    /**
     * Create a new product (admin only)
     */
    @Operation(summary = "Create a new product (admin only)")
    ResponseEntity<ProductResponseDTO> create(ProductRequestDTO dto);

    /**
     * Update a product by ID (admin only)
     */
    @Operation(summary = "Update product by ID (admin only)")
    ResponseEntity<ProductResponseDTO> update(Long id, ProductRequestDTO dto);

    /**
     * Delete a product by ID (admin only)
     */
    @Operation(summary = "Delete product by ID (admin only)")
    ResponseEntity<Void> delete(Long id);

    /**
     * Update product quantity
     */
    @Operation(summary = "Update product quantity")
    ResponseEntity<ProductResponseDTO> updateQuantity(Long id, int quantity);
}