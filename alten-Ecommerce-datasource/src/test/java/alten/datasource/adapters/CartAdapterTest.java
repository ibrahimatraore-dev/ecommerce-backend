package alten.datasource.adapters;

import alten.core.dtos.cart.CartItemDTO;
import alten.core.entities.CartItem;
import alten.core.entities.Product;
import alten.core.entities.User;
import alten.datasource.entities.CartItemEntity;
import alten.datasource.entities.ProductEntity;
import alten.datasource.entities.UserEntity;
import alten.datasource.repositories.ICartItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartAdapterTest {

    @Mock
    private ICartItemRepository cartRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CartAdapter cartAdapter;

    @Test
    void testSave() {
        CartItem item = new CartItem();
        CartItemEntity entity = new CartItemEntity();
        CartItemEntity savedEntity = new CartItemEntity();
        CartItem expected = new CartItem();

        when(modelMapper.map(item, CartItemEntity.class)).thenReturn(entity);
        when(cartRepository.save(entity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, CartItem.class)).thenReturn(expected);

        CartItem result = cartAdapter.save(item);

        assertEquals(expected, result);
    }

    @Test
    void testDeleteByUserIdAndProductId() {
        cartAdapter.deleteByUserIdAndProductId(1L, 2L);
        verify(cartRepository).deleteByUserIdAndProductId(1L, 2L);
    }

    @Test
    void testFindByUserId() {
        CartItemEntity entity = new CartItemEntity();
        CartItem cartItem = new CartItem();

        when(cartRepository.findByUserId(1L)).thenReturn(List.of(entity));
        when(modelMapper.map(entity, CartItem.class)).thenReturn(cartItem);

        List<CartItem> result = cartAdapter.findByUserId(1L);

        assertEquals(1, result.size());
        assertEquals(cartItem, result.get(0));
    }

    @Test
    void testAddToCart_newItem() {
        User user = new User();
        user.setId(1L);
        Product product = new Product();
        product.setId(2L);
        CartItemEntity entity = new CartItemEntity();

        when(cartRepository.findByUserIdAndProductId(1L, 2L)).thenReturn(Optional.empty());
        when(modelMapper.map(user, UserEntity.class)).thenReturn(new UserEntity());
        when(modelMapper.map(product, ProductEntity.class)).thenReturn(new ProductEntity());

        cartAdapter.addToCart(user, product, 3);

        verify(cartRepository).save(any(CartItemEntity.class));
    }

    @Test
    void testRemoveFromCart() {
        User user = new User();
        user.setId(1L);

        cartAdapter.removeFromCart(user, 2L);

        verify(cartRepository).deleteByUserIdAndProductId(1L, 2L);
    }

    @Test
    void testUpdateCartQuantity() {
        User user = new User();
        user.setId(1L);
        CartItemEntity item = new CartItemEntity();

        when(cartRepository.findByUserIdAndProductId(1L, 2L)).thenReturn(Optional.of(item));

        cartAdapter.updateCartQuantity(user, 2L, 5);

        assertEquals(5, item.getQuantity());
        verify(cartRepository).save(item);
    }

    @Test
    void testUpdateCartQuantity_itemNotFound_shouldThrow() {
        User user = new User();
        user.setId(1L);

        when(cartRepository.findByUserIdAndProductId(1L, 2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartAdapter.updateCartQuantity(user, 2L, 5));
    }

    @Test
    void testGetCart() {
        CartItemEntity entity = new CartItemEntity();
        CartItemDTO dto = new CartItemDTO();
        User user = new User();
        user.setId(1L);

        when(cartRepository.findByUserId(1L)).thenReturn(List.of(entity));
        when(modelMapper.map(entity, CartItemDTO.class)).thenReturn(dto);

        List<CartItemDTO> result = cartAdapter.getCart(user);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }
}