package alten.datasource.adapters;

import alten.core.dtos.cart.CartItemDTO;
import alten.core.entities.CartItem;
import alten.core.entities.Product;
import alten.core.entities.User;
import alten.core.ports.datasources.ICartPort;
import alten.datasource.entities.CartItemEntity;
import alten.datasource.entities.ProductEntity;
import alten.datasource.entities.UserEntity;
import alten.datasource.repositories.ICartItemRepository;
import alten.datasource.repositories.IProductRepository;
import alten.datasource.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartAdapter implements ICartPort {

    private final ICartItemRepository cartRepository;
    private final ModelMapper modelMapper;


    @Override
    public CartItem save(CartItem item) {
        CartItemEntity entity = modelMapper.map(item, CartItemEntity.class);
        return modelMapper.map(cartRepository.save(entity), CartItem.class);
    }

    @Override
    public void deleteByUserIdAndProductId(Long userId, Long productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    public List<CartItem> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .stream()
                .map(e -> modelMapper.map(e, CartItem.class))
                .collect(Collectors.toList());
    }


    @Override
    public void addToCart(User user, Product product, int quantity) {
        CartItemEntity item = cartRepository.findByUserIdAndProductId(user.getId(), product.getId())
                .orElse(new CartItemEntity());

        item.setUser(modelMapper.map(user, UserEntity.class));
        item.setProduct(modelMapper.map(product, ProductEntity.class));
        item.setQuantity(item.getId() == null ? quantity : item.getQuantity() + quantity);
        cartRepository.save(item);
    }

    @Override
    public void removeFromCart(User user, Long productId) {
        cartRepository.deleteByUserIdAndProductId(user.getId(), productId);
    }

    @Override
    public void updateCartQuantity(User user, Long productId, int quantity) {
        CartItemEntity item = cartRepository.findByUserIdAndProductId(user.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        item.setQuantity(quantity);
        cartRepository.save(item);
    }

    @Override
    public List<CartItemDTO> getCart(User user) {
        return cartRepository.findByUserId(user.getId())
                .stream()
                .map(e -> modelMapper.map(e, CartItemDTO.class))
                .collect(Collectors.toList());
    }

}