package alten.datasource.adapters;

import alten.core.entities.Product;
import alten.datasource.entities.ProductEntity;
import alten.datasource.mappers.IProductEntityMapper;
import alten.datasource.repositories.IProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductAdapterTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IProductEntityMapper productEntityMapper;

    @InjectMocks
    private ProductAdapter productAdapter;

    @Test
    void testSave_shouldSaveAndReturnProduct() {
        Product product = new Product();
        ProductEntity entity = new ProductEntity();
        ProductEntity savedEntity = new ProductEntity();
        Product expected = new Product();

        when(productEntityMapper.toEntity(product)).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(savedEntity);
        when(productEntityMapper.toDomain(savedEntity)).thenReturn(expected);

        Product result = productAdapter.save(product);

        assertEquals(expected, result);
    }

    @Test
    void testDeleteById_shouldCallRepository() {
        Long id = 1L;

        productAdapter.deleteById(id);

        verify(productRepository).deleteById(id);
    }

    @Test
    void testFindById_shouldReturnProduct() {
        Long id = 1L;
        ProductEntity entity = new ProductEntity();
        Product product = new Product();

        when(productRepository.findById(id)).thenReturn(Optional.of(entity));
        when(productEntityMapper.toDomain(entity)).thenReturn(product);

        Optional<Product> result = productAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void testFindById_shouldReturnEmpty() {
        Long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Product> result = productAdapter.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll_withKeyword() {
        String keyword = "phone";
        Pageable pageable = PageRequest.of(0, 10);
        ProductEntity entity = new ProductEntity();
        Product product = new Product();

        Page<ProductEntity> pageEntity = new PageImpl<>(List.of(entity));

        when(productRepository.findByNameContainingIgnoreCase(keyword, pageable)).thenReturn(pageEntity);
        when(productEntityMapper.toDomain(entity)).thenReturn(product);

        Page<Product> result = productAdapter.findAll(keyword, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(product, result.getContent().get(0));
    }

    @Test
    void testFindAll_withoutKeyword() {
        String keyword = "";
        Pageable pageable = PageRequest.of(0, 10);
        ProductEntity entity = new ProductEntity();
        Product product = new Product();

        Page<ProductEntity> pageEntity = new PageImpl<>(List.of(entity));

        when(productRepository.findAll(pageable)).thenReturn(pageEntity);
        when(productEntityMapper.toDomain(entity)).thenReturn(product);

        Page<Product> result = productAdapter.findAll(keyword, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(product, result.getContent().get(0));
    }
}