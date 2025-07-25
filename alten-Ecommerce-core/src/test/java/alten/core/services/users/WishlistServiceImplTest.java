package alten.core.services.wishlist;

import alten.core.converters.IProductMapper;
import alten.core.dtos.user.UserDTO;
import alten.core.dtos.wishlist.WishlistItemDTO;
import alten.core.entities.Product;
import alten.core.entities.User;
import alten.core.entities.WishlistItem;
import alten.core.ports.datasources.IWishlistPort;
import alten.core.services.products.IProductService;
import alten.core.services.users.IUsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistServiceImplTest {

    @Mock
    private IWishlistPort wishlistPort;

    @Mock
    private IUsersService usersService;

    @Mock
    private IProductService productService;

    @Mock
    private IProductMapper productMapper;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    private UserDTO userDTO;
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setId(1L);

        user = new User();
        user.setId(1L);

        product = new Product();
        product.setId(10L);
         //ignorer
        lenient().when(usersService.getCurrentUser()).thenReturn(userDTO);
        lenient().when(modelMapper.map(userDTO, User.class)).thenReturn(user);
    }


    @Test
    void testAddToWishlist_shouldCallSave() {
        when(productService.findById(10L)).thenReturn(null);
        when(productMapper.fromResponse(null)).thenReturn(product);

        wishlistService.addToWishlist(10L);

        verify(wishlistPort).save(any(WishlistItem.class));
    }

    @Test
    void testRemoveFromWishlist_shouldCallDelete() {
        wishlistService.removeFromWishlist(10L);
        verify(wishlistPort).deleteByUserIdAndProductId(1L, 10L);
    }

    @Test
    void testGetWishlist_shouldReturnList() {
        WishlistItem item = new WishlistItem();
        WishlistItemDTO dto = new WishlistItemDTO();

        when(wishlistPort.findByUserId(1L)).thenReturn(List.of(item));
        when(modelMapper.map(item, WishlistItemDTO.class)).thenReturn(dto);

        List<WishlistItemDTO> result = wishlistService.getWishlist();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }
}
