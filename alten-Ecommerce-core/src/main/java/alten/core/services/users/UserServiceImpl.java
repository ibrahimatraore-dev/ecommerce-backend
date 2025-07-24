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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements IUsersService {

    private final IUserPort userPort;
    private final IUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ICurrentUserService currentUserService;

    @Override
    public UserDTO getCurrentUser() {
        log.debug("Getting current authenticated user");
        return currentUserService.getCurrentUser();
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        log.info("Searching user by email: {}", email);
        Optional<User> user = userPort.findByEmail(email);
        return user.map(this::toDTO);
    }

    @Override
    public Boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN.name()));
        log.debug("Current user admin check: {}", isAdmin);
        return isAdmin;
    }

    @Override
    public Optional<AuthUserDetailsDTO> findAuthByEmail(String email) {
        log.info("Loading authentication details for email: {}", email);
        Optional<User> user = userPort.findByEmail(email);
        return user.map(u -> {
            AuthUserDetailsDTO authUserDetailsDTO = new AuthUserDetailsDTO();
            authUserDetailsDTO.setEmailAddress(u.getEmailAddress());
            authUserDetailsDTO.setPassword(u.getPassword());
            authUserDetailsDTO.setRole(u.getRole());
            return authUserDetailsDTO;
        });
    }

    @Override
    public UserDTO registerUser(UserUpdateDTO dto) {
        log.info("Attempting to register new user with email: {}", dto.getEmailAddress());

        if (userPort.findByEmail(dto.getEmailAddress()).isPresent()) {
            log.warn("Registration failed: user with email {} already exists", dto.getEmailAddress());
            throw new ConcurrencyFailureException("User already exists");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmailAddress(dto.getEmailAddress());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());

        User savedUser = userPort.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateDTO dto) {
        log.info("Updating user with ID: {}", id);

        User user = userPort.findById(id)
                .orElseThrow(() -> {
                    log.error("User update failed: user with ID {} not found", id);
                    return new ResourceNotFoundException("User not found");
                });

        user.setEmailAddress(dto.getEmailAddress());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            log.debug("Updating password for user ID: {}", id);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updatedUser = userPort.save(user);
        log.info("User with ID: {} updated successfully", updatedUser.getId());
        return userMapper.toDTO(updatedUser);
    }

    @Override
    public UserDTO getUser() {
        Long userId = getCurrentUser().getId();
        log.info("Fetching user with ID: {}", userId);

        User user = userPort.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new ResourceNotFoundException("User not found");
                });

        log.info("User with ID: {} retrieved successfully", userId);
        return userMapper.toDTO(user);
    }

    @Override
    public boolean isCurrentUserAdminEmail() {
        UserDTO currentUser = getCurrentUser();
        boolean isAdmin = "admin@admin.com".equalsIgnoreCase(currentUser.getEmailAddress());
        log.debug("Is current user admin@admin.com? {}", isAdmin);
        return isAdmin;
    }

    private UserDTO toDTO(User u) {
        return userMapper.toDTO(u);
    }
}