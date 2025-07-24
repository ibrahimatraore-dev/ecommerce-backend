package alten.datasource.adapters;


import alten.core.entities.WishlistItem;
import alten.core.ports.datasources.IWishlistPort;
import alten.datasource.entities.WishlistItemEntity;
import alten.datasource.repositories.IWishlistItemRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WishlistAdapter implements IWishlistPort {

    private final IWishlistItemRepository wishlistRepository;
    private final ModelMapper modelMapper;

    @Override
    public WishlistItem save(WishlistItem item) {
        WishlistItemEntity entity = modelMapper.map(item, WishlistItemEntity.class);
        return modelMapper.map(wishlistRepository.save(entity), WishlistItem.class);
    }

    @Override
    public void deleteByUserIdAndProductId(Long userId, Long productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    public List<WishlistItem> findByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId)
                .stream()
                .map(e -> modelMapper.map(e, WishlistItem.class))
                .collect(Collectors.toList());
    }
}
