package alten.api.resources.users;

import alten.core.dtos.auth.AuthUserDetailsDTO;
import alten.core.services.users.IUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final IUsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserDetailsDTO user = usersService.findAuthByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return User.builder()
                .username(user.getEmailAddress())
                .password(user.getPassword())
                .roles(new String[]{user.getRole().name()})
                .build();
    }
}
