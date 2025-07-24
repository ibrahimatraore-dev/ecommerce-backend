package alten.core.services.users;

import alten.core.converters.IUserMapper;
import alten.core.dtos.user.UserDTO;
import alten.core.ports.datasources.IUserPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService implements ICurrentUserService {

    private final IUserPort userPort;
    private final IUserMapper userMapper;

    @Override
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userPort.findByEmail(email)
                .map(userMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new);
    }
}