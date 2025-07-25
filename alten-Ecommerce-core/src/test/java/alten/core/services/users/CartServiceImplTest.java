package alten.core.services.cart;

import alten.core.converters.IProductMapper;
import alten.core.converters.IUserMapper;
import alten.core.dtos.cart.CartItemDTO;
import alten.core.dtos.user.UserDTO;
import alten.core.entities.Product;
import alten.core.entities.User;
import alten.core.ports.datasources.ICartPort;
import alten.core.services.products.IProductService;
import alten.core.services.users.IUsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private ICartPort cartPort;

    @Mock
    private IProductService productService;

    @Mock
    private IUsersService usersService;

    @Mock
    private IProductMapper productMapper;

    @Mock
    private IUserMapper userMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Product product;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        userDTO = new UserDTO();
        userDTO.setEmailAddress("test@example.com");

        product = new Product();
        product.setId(2L);

        when(usersService.getCurrentUser()).thenReturn(userDTO);
        when(userMapper.toEntity(userDTO)).thenReturn(user);
    }

    @Test
    void testAddToCart_shouldCallCartPort() {
        when(productService.findById(2L)).thenReturn(null);
        when(productMapper.fromResponse(null)).thenReturn(product);

        cartService.addToCart(2L, 3);

        verify(cartPort).addToCart(user, product, 3);
    }

    @Test
    void testRemoveFromCart_shouldCallCartPort() {
        cartService.removeFromCart(2L);
        verify(cartPort).removeFromCart(user, 2L);
    }

    @Test
    void testGetCart_shouldReturnList() {
        CartItemDTO item = new CartItemDTO();
        when(cartPort.getCart(user)).thenReturn(List.of(item));

        List<CartItemDTO> result = cartService.getCart();

        assertEquals(1, result.size());
        assertEquals(item, result.get(0));
    }

    @Test
    void testUpdateCartItemQuantity_shouldCallCartPort() {
        cartService.updateCartItemQuantity(2L, 5);
        verify(cartPort).updateCartQuantity(user, 2L, 5);
    }
}