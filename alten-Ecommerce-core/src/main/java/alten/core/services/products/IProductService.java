package alten.core.services.products;

import alten.core.dtos.product.ProductRequestDTO;
import alten.core.dtos.product.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing products.
 */
public interface IProductService {

    /**
     * Create a new product. Only accessible to admin users.
     *
     * @param dto Product data to create
     * @return Created product details
     */
    ProductResponseDTO create(ProductRequestDTO dto);

    /**
     * Update an existing product by ID. Only accessible to admin users.
     *
     * @param id  ID of the product to update
     * @param dto New product data
     * @return Updated product details
     */
    ProductResponseDTO update(Long id, ProductRequestDTO dto);

    /**
     * Delete a product by ID. Only accessible to admin users.
     *
     * @param id ID of the product to delete
     */
    void delete(Long id);

    /**
     * List all products with optional keyword filtering and pagination.
     *
     * @param keyword  Optional keyword to filter products
     * @param pageable Pagination information
     * @return Paginated list of products
     */
    Page<ProductResponseDTO> list(String keyword, Pageable pageable);

    /**
     * Retrieve a product by its ID.
     *
     * @param id Product ID
     * @return Product details
     */
    ProductResponseDTO findById(Long id);

    /**
     * Update the quantity of a specific product. Only accessible to admin users.
     *
     * @param id       Product ID
     * @param quantity New quantity value
     * @return Updated product details
     */
    ProductResponseDTO updateQuantity(Long id, int quantity);
}