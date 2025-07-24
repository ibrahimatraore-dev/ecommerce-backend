package alten.datasource.adapters;

import alten.core.entities.User;
import alten.datasource.entities.UserEntity;
import alten.datasource.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserAdapter userAdapter;

    @Test
    void testFindByEmail_shouldReturnUser() {
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity();
        User user = new User();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(userEntity, User.class)).thenReturn(user);

        Optional<User> result = userAdapter.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(userRepository).findByEmail(email);
        verify(modelMapper).map(userEntity, User.class);
    }

    @Test
    void testFindByEmail_shouldReturnEmpty() {
        String email = "notfound@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userAdapter.findByEmail(email);

        assertFalse(result.isPresent());

        verify(userRepository).findByEmail(email);
        verify(modelMapper, never()).map(any(), eq(User.class));
    }

    @Test
    void testSave_shouldSaveAndReturnMappedUser() {
        User user = new User();
        UserEntity userEntity = new UserEntity();
        UserEntity savedEntity = new UserEntity();
        User mappedUser = new User();

        when(modelMapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, User.class)).thenReturn(mappedUser);

        User result = userAdapter.save(user);

        assertEquals(mappedUser, result);

        verify(modelMapper).map(user, UserEntity.class);
        verify(userRepository).save(userEntity);
        verify(modelMapper).map(savedEntity, User.class);
    }

    @Test
    void testFindById_shouldReturnUser() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        User user = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(userEntity, User.class)).thenReturn(user);

        Optional<User> result = userAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(userRepository).findById(id);
        verify(modelMapper).map(userEntity, User.class);
    }

    @Test
    void testFindById_shouldReturnEmpty() {
        Long id = 2L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<User> result = userAdapter.findById(id);

        assertFalse(result.isPresent());

        verify(userRepository).findById(id);
        verify(modelMapper, never()).map(any(), eq(User.class));
    }
}
