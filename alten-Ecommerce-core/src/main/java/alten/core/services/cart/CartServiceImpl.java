package alten.core.services.cart;

import alten.core.converters.IProductMapper;
import alten.core.converters.IUserMapper;
import alten.core.dtos.cart.CartItemDTO;
import alten.core.entities.Product;
import alten.core.entities.User;
import alten.core.ports.datasources.ICartPort;
import alten.core.services.products.IProductService;
import alten.core.services.users.IUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements ICartService {

    private final ICartPort cartPort;
    private final IProductService productService;
    private final IUsersService usersService;
    private final IProductMapper productMapper;
    private final IUserMapper userMapper;

    @Override
    public void addToCart(Long productId, int quantity) {
        log.info("Adding product (ID: {}) to cart with quantity: {}", productId, quantity);
        User user = userMapper.toEntity(usersService.getCurrentUser());
        Product product = productMapper.fromResponse(productService.findById(productId));
        cartPort.addToCart(user, product, quantity);
        log.info("Product (ID: {}) added to cart successfully", productId);
    }

    @Override
    public void removeFromCart(Long productId) {
        log.info("Removing product (ID: {}) from cart", productId);
        User user = userMapper.toEntity(usersService.getCurrentUser());
        cartPort.removeFromCart(user, productId);
        log.info("Product (ID: {}) removed from cart", productId);
    }

    @Override
    public List<CartItemDTO> getCart() {
        log.info("Fetching cart for current user");
        User user = userMapper.toEntity(usersService.getCurrentUser());
        List<CartItemDTO> cart = cartPort.getCart(user);
        log.info("Cart retrieved successfully with {} item(s)", cart.size());
        return cart;
    }

    @Override
    public void updateCartItemQuantity(Long productId, int quantity) {
        log.info("Updating quantity for product (ID: {}) to {}", productId, quantity);
        User user = userMapper.toEntity(usersService.getCurrentUser());
        cartPort.updateCartQuantity(user, productId, quantity);
        log.info("Quantity updated for product (ID: {})", productId);
    }
}