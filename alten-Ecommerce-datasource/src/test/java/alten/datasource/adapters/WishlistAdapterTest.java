package alten.datasource.adapters;

import alten.core.entities.WishlistItem;
import alten.datasource.entities.WishlistItemEntity;
import alten.datasource.repositories.IWishlistItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistAdapterTest {

    @Mock
    private IWishlistItemRepository wishlistRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private WishlistAdapter wishlistAdapter;

    @Test
    void testSave_shouldSaveAndReturnWishlistItem() {
        WishlistItem item = new WishlistItem();
        WishlistItemEntity entity = new WishlistItemEntity();
        WishlistItemEntity savedEntity = new WishlistItemEntity();
        WishlistItem expected = new WishlistItem();

        when(modelMapper.map(item, WishlistItemEntity.class)).thenReturn(entity);
        when(wishlistRepository.save(entity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, WishlistItem.class)).thenReturn(expected);

        WishlistItem result = wishlistAdapter.save(item);

        assertEquals(expected, result);
    }

    @Test
    void testDeleteByUserIdAndProductId() {
        wishlistAdapter.deleteByUserIdAndProductId(1L, 2L);
        verify(wishlistRepository).deleteByUserIdAndProductId(1L, 2L);
    }

    @Test
    void testFindByUserId_shouldReturnMappedList() {
        WishlistItemEntity entity = new WishlistItemEntity();
        WishlistItem item = new WishlistItem();

        when(wishlistRepository.findByUserId(1L)).thenReturn(List.of(entity));
        when(modelMapper.map(entity, WishlistItem.class)).thenReturn(item);

        List<WishlistItem> result = wishlistAdapter.findByUserId(1L);

        assertEquals(1, result.size());
        assertEquals(item, result.get(0));
    }
}