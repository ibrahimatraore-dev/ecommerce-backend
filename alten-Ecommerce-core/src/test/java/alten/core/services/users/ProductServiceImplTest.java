package alten.core.services.products;

import alten.core.converters.IProductMapper;
import alten.core.dtos.product.ProductRequestDTO;
import alten.core.dtos.product.ProductResponseDTO;
import alten.core.entities.Product;
import alten.core.exceptions.ResourceNotFoundException;
import alten.core.ports.datasources.IProductPort;
import alten.core.services.users.IUsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private IProductPort productPort;

    @Mock
    private IProductMapper productMapper;

    @Mock
    private IUsersService usersService;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductRequestDTO requestDTO;
    private Product product;
    private ProductResponseDTO responseDTO;

    @BeforeEach
    void setup() {
        requestDTO = new ProductRequestDTO();
        requestDTO.setName("Test Product");

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Test Product");

        when(usersService.isCurrentUserAdminEmail()).thenReturn(true);
    }

    @Test
    void create_shouldReturnResponseDTO() {
        when(productMapper.toEntity(requestDTO)).thenReturn(product);
        when(productPort.save(any())).thenReturn(product);
        when(productMapper.toResponseDTO(product)).thenReturn(responseDTO);

        ProductResponseDTO result = productService.create(requestDTO);

        assertEquals(responseDTO, result);
    }

    @Test
    void update_shouldReturnUpdatedResponseDTO() {
        Long id = 1L;
        when(productPort.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toEntity(requestDTO)).thenReturn(product);
        when(productPort.save(any())).thenReturn(product);
        when(productMapper.toResponseDTO(product)).thenReturn(responseDTO);

        ProductResponseDTO result = productService.update(id, requestDTO);

        assertEquals(responseDTO, result);
    }

    @Test
    void update_shouldThrowIfProductNotFound() {
        when(productPort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.update(99L, requestDTO));
    }

    @Test
    void delete_shouldCallPortDelete() {
        productService.delete(1L);
        verify(productPort).deleteById(1L);
    }



    @Test
    void updateQuantity_shouldUpdateAndReturnDTO() {
        when(productPort.findById(1L)).thenReturn(Optional.of(product));
        when(productPort.save(any())).thenReturn(product);
        when(productMapper.toResponseDTO(product)).thenReturn(responseDTO);

        ProductResponseDTO result = productService.updateQuantity(1L, 50);

        assertEquals(responseDTO, result);
        assertEquals(50, product.getQuantity());
    }

    @Test
    void updateQuantity_shouldThrowIfNotFound() {
        when(productPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.updateQuantity(1L, 50));
    }

    @Test
    void shouldThrowAccessDeniedIfNotAdmin() {
        when(usersService.isCurrentUserAdminEmail()).thenReturn(false);
        assertThrows(AccessDeniedException.class, () -> productService.create(requestDTO));
    }
}