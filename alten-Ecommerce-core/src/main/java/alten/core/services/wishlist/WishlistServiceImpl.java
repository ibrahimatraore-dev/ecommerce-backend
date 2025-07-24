package alten.core.services.wishlist;

import alten.core.converters.IProductMapper;
import alten.core.converters.IWishlistMapper;
import alten.core.dtos.wishlist.WishlistItemDTO;
import alten.core.entities.Product;
import alten.core.entities.User;
import alten.core.entities.WishlistItem;
import alten.core.ports.datasources.IWishlistPort;
import alten.core.services.products.IProductService;
import alten.core.services.users.IUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishlistServiceImpl implements IWishlistService {

    private final IWishlistPort wishlistPort;
    private final IUsersService usersService;
    private final IProductService productService;
    private final IProductMapper productMapper;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void addToWishlist(Long productId) {
        log.info("Adding product {} to wishlist", productId);
        User user = modelMapper.map(usersService.getCurrentUser(), User.class);
        Product product = productMapper.fromResponse(productService.findById(productId));

        WishlistItem item = new WishlistItem();
        item.setUser(user);
        item.setProduct(product);
        wishlistPort.save(item);
        log.info("Product {} added to wishlist for user {}", productId, user.getId());
    }

    @Transactional
    @Override
    public void removeFromWishlist(Long productId) {
        Long userId = usersService.getCurrentUser().getId();
        log.info("Removing product {} from wishlist for user {}", productId, userId);
        wishlistPort.deleteByUserIdAndProductId(userId, productId);
        log.info("Product {} removed from wishlist for user {}", productId, userId);
    }

    @Transactional
    @Override
    public List<WishlistItemDTO> getWishlist() {
        Long userId = usersService.getCurrentUser().getId();
        log.info("Retrieving wishlist for user {}", userId);

        List<WishlistItemDTO> wishlist = wishlistPort.findByUserId(userId)
                .stream()
                .map(item -> modelMapper.map(item, WishlistItemDTO.class))
                .collect(Collectors.toList());

        log.info("Wishlist for user {} retrieved with {} item(s)", userId, wishlist.size());
        return wishlist;
    }
}