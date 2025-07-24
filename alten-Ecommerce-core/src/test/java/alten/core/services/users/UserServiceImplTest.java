package alten.core.services.users;

import alten.core.converters.IUserMapper;
import alten.core.dtos.auth.AuthUserDetailsDTO;
import alten.core.dtos.user.UserDTO;
import alten.core.dtos.user.UserUpdateDTO;
import alten.core.entities.User;
import alten.core.entities.enums.UserRole;
import alten.core.exceptions.ResourceNotFoundException;
import alten.core.ports.datasources.IUserPort;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IUserPort userPort;

    @Mock
    private IUserMapper userMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Mock
    private CurrentUserService currentUserService;


    private User testUser;
    private UserDTO testUserDTO;
    private AuthUserDetailsDTO testAuthUserDetailsDTO;

    @BeforeEach
    void setUp() {
        // Configuration des objets de test
        testUser = new User();
        testUser.setEmailAddress("test@example.com");
        testUser.setPassword("password123");
        testUser.setRole(UserRole.USER);

        testUserDTO = new UserDTO();
        testUserDTO.setEmailAddress("test@example.com");

        testAuthUserDetailsDTO = new AuthUserDetailsDTO();
        testAuthUserDetailsDTO.setEmailAddress("test@example.com");
        testAuthUserDetailsDTO.setPassword("password123");
        testAuthUserDetailsDTO.setRole(UserRole.USER);
    }

    @Test
    void getCurrentUser_WhenUserExists_ShouldReturnUserDTO() {
        // Given
        String email = "test@example.com";
        testUserDTO.setEmailAddress(email);

        when(currentUserService.getCurrentUser()).thenReturn(testUserDTO);

        // When
        UserDTO result = userService.getCurrentUser();

        // Then
        assertNotNull(result);
        assertEquals(email, result.getEmailAddress());
        verify(currentUserService).getCurrentUser();
    }


    @Test
    void getCurrentUser_WhenServiceThrowsException_ShouldPropagateException() {
        // Given
        when(currentUserService.getCurrentUser()).thenThrow(new EntityNotFoundException("User not found"));

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> userService.getCurrentUser());
        verify(currentUserService).getCurrentUser();
    }


    @Test
    void findByEmail_WhenUserExists_ShouldReturnOptionalUserDTO() {
        // Given
        String email = "test@example.com";
        when(userPort.findByEmail(email)).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // When
        Optional<UserDTO> result = userService.findByEmail(email);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testUserDTO.getEmailAddress(), result.get().getEmailAddress());
        verify(userPort).findByEmail(email);
        verify(userMapper).toDTO(testUser);
    }

    @Test
    void findByEmail_WhenUserDoesNotExist_ShouldReturnEmptyOptional() {
        // Given
        String email = "nonexistent@example.com";
        when(userPort.findByEmail(email)).thenReturn(Optional.empty());

        // When
        Optional<UserDTO> result = userService.findByEmail(email);

        // Then
        assertFalse(result.isPresent());
        verify(userPort).findByEmail(email);
        verify(userMapper, never()).toDTO(any(User.class));
    }

    @Test
    void isCurrentUserAdmin_WhenUserIsAdmin_ShouldReturnTrue() {
        // Given
        Collection<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN.name())
        );
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // When
            Boolean result = userService.isCurrentUserAdmin();

            // Then
            assertTrue(result);
        }
    }

    @Test
    void isCurrentUserAdmin_WhenUserIsNotAdmin_ShouldReturnFalse() {
        // Given
        Collection<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + UserRole.USER.name())
        );
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // When
            Boolean result = userService.isCurrentUserAdmin();

            // Then
            assertFalse(result);
        }
    }

    @Test
    void isCurrentUserAdmin_WhenUserHasNoAuthorities_ShouldReturnFalse() {
        // Given
        Collection<SimpleGrantedAuthority> authorities = List.of();
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // When
            Boolean result = userService.isCurrentUserAdmin();

            // Then
            assertFalse(result);
        }
    }

    @Test
    void findAuthByEmail_WhenUserExists_ShouldReturnOptionalAuthUserDetailsDTO() {
        // Given
        String email = "test@example.com";
        when(userPort.findByEmail(email)).thenReturn(Optional.of(testUser));

        // When
        Optional<AuthUserDetailsDTO> result = userService.findAuthByEmail(email);

        // Then
        assertTrue(result.isPresent());
        AuthUserDetailsDTO authUserDetails = result.get();
        assertEquals(testUser.getEmailAddress(), authUserDetails.getEmailAddress());
        assertEquals(testUser.getPassword(), authUserDetails.getPassword());
        assertEquals(testUser.getRole(), authUserDetails.getRole());
        verify(userPort).findByEmail(email);
    }

    @Test
    void findAuthByEmail_WhenUserDoesNotExist_ShouldReturnEmptyOptional() {
        // Given
        String email = "nonexistent@example.com";
        when(userPort.findByEmail(email)).thenReturn(Optional.empty());

        // When
        Optional<AuthUserDetailsDTO> result = userService.findAuthByEmail(email);

        // Then
        assertFalse(result.isPresent());
        verify(userPort).findByEmail(email);
    }

    @Test
    void findAuthByEmail_WithNullEmail_ShouldCallUserPortWithNull() {
        // Given
        when(userPort.findByEmail(null)).thenReturn(Optional.empty());

        // When
        Optional<AuthUserDetailsDTO> result = userService.findAuthByEmail(null);

        // Then
        assertFalse(result.isPresent());
        verify(userPort).findByEmail(null);
    }

    @Test
    void findAuthByEmail_WithEmptyEmail_ShouldCallUserPortWithEmptyString() {
        // Given
        String email = "";
        when(userPort.findByEmail(email)).thenReturn(Optional.empty());

        // When
        Optional<AuthUserDetailsDTO> result = userService.findAuthByEmail(email);

        // Then
        assertFalse(result.isPresent());
        verify(userPort).findByEmail(email);
    }

    @Test
    void findByEmail_WithNullEmail_ShouldCallUserPortWithNull() {
        // Given
        when(userPort.findByEmail(null)).thenReturn(Optional.empty());

        // When
        Optional<UserDTO> result = userService.findByEmail(null);

        // Then
        assertFalse(result.isPresent());
        verify(userPort).findByEmail(null);
        verify(userMapper, never()).toDTO(any(User.class));
    }

    @Test
    void isCurrentUserAdmin_WhenUserHasMultipleRoles_ShouldReturnTrueIfAdminRolePresent() {
        // Given
        Collection<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + UserRole.USER.name()),
                new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN.name())
        );
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // When
            Boolean result = userService.isCurrentUserAdmin();

            // Then
            assertTrue(result);
        }
    }

    @Test
    void registerUser_WhenUserDoesNotExist_ShouldRegisterSuccessfully() {
        // Given
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setEmailAddress("new@example.com");
        dto.setPassword("plainPassword");
        dto.setFirstName("John");
        dto.setLastName("Doe");

        User mappedUser = new User();
        mappedUser.setEmailAddress(dto.getEmailAddress());

        User savedUser = new User();
        savedUser.setEmailAddress(dto.getEmailAddress());

        UserDTO expectedDto = new UserDTO();
        expectedDto.setEmailAddress(dto.getEmailAddress());

        when(userPort.findByEmail(dto.getEmailAddress())).thenReturn(Optional.empty());
        when(userMapper.toEntity(dto)).thenReturn(mappedUser);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(userPort.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toDTO(savedUser)).thenReturn(expectedDto);

        // When
        UserDTO result = userService.registerUser(dto);

        // Then
        assertNotNull(result);
        assertEquals(dto.getEmailAddress(), result.getEmailAddress());
        verify(userPort).findByEmail(dto.getEmailAddress());
        verify(passwordEncoder).encode(dto.getPassword());
        verify(userPort).save(any(User.class));
        verify(userMapper).toDTO(savedUser);
    }

    @Test
    void registerUser_WhenUserAlreadyExists_ShouldThrowConcurrencyFailureException() {
        // Given
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setEmailAddress("existing@example.com");

        when(userPort.findByEmail(dto.getEmailAddress())).thenReturn(Optional.of(new User()));

        // When & Then
        assertThrows(ConcurrencyFailureException.class, () -> userService.registerUser(dto));
        verify(userPort).findByEmail(dto.getEmailAddress());
        verifyNoMoreInteractions(userMapper, passwordEncoder, userPort);
    }

    @Test
    void updateUser_WhenUserExists_ShouldUpdateAndReturnDTO() {
        // Given
        Long userId = 1L;
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setLastName("Doe");
        dto.setFirstName("Jane");
        dto.setPosition("Manager");
        dto.setGender(UserGender.MALE);
        dto.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(userId);

        User updatedUser = new User();
        updatedUser.setId(userId);

        UserDTO updatedDto = new UserDTO();
        updatedDto.setEmailAddress("updated@example.com");

        when(userPort.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedNewPassword");
        when(userPort.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toDTO(updatedUser)).thenReturn(updatedDto);

        // When
        UserDTO result = userService.updateUser(userId, dto);

        // Then
        assertNotNull(result);
        verify(userPort).findById(userId);
        verify(passwordEncoder).encode(dto.getPassword());
        verify(userPort).save(existingUser);
        verify(userMapper).toDTO(updatedUser);
    }

    @Test
    void updateUser_WhenUserNotFound_ShouldThrowResourceNotFoundException() {

        Long userId = 999L;
        UserUpdateDTO dto = new UserUpdateDTO();

        when(userPort.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userId, dto));
        verify(userPort).findById(userId);
        verifyNoMoreInteractions(userPort, passwordEncoder, userMapper);
    }


    @Test
    void acceptCGU_ShouldSetCguAcceptedTrueAndSaveUser() {

        String email = "test@example.com";

        testUserDTO.setEmailAddress(email);
        testUser.setEmailAddress(email);
        testUser.setCguAccepted(false);

        when(currentUserService.getCurrentUser()).thenReturn(testUserDTO);
        when(userPort.findByEmail(email)).thenReturn(Optional.of(testUser));

        userService.acceptCGU();

        assertTrue(testUser.getCguAccepted());
        verify(currentUserService).getCurrentUser();
        verify(userPort, times(1)).findByEmail(email);
        verify(userPort).save(testUser);
    }


}