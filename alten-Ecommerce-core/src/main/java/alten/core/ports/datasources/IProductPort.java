package alten.core.ports.datasources;

import alten.core.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Data access interface for Product entity.
 * This abstraction allows interaction with the data source (e.g., database)
 * without exposing implementation details.
 */
public interface IProductPort {

    /**
     * Save a product (create or update).
     *
     * @param product the product entity to save
     * @return the saved product entity
     */
    Product save(Product product);

    /**
     * Delete a product by its ID.
     *
     * @param id the ID of the product to delete
     */
    void deleteById(Long id);

    /**
     * Find a product by its ID.
     *
     * @param id the ID of the product
     * @return an Optional containing the product if found, or empty otherwise
     */
    Optional<Product> findById(Long id);

    /**
     * Find all products with optional keyword filtering and pagination.
     *
     * @param keyword optional keyword for searching products
     * @param pageable pagination information
     * @return a paginated list of matching products
     */
    Page<Product> findAll(String keyword, Pageable pageable);
}