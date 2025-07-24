package alten.datasource.repositories;

import alten.datasource.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Search for products where the name contains the given keyword (case-insensitive).
     *
     * @param name     the keyword to search for in product names
     * @param pageable pagination information
     * @return a page of matching products
     */
    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
