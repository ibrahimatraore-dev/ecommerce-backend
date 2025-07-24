package alten.datasource.adapters;

import alten.core.entities.User;
import alten.core.ports.datasources.IUserPort;
import alten.datasource.entities.UserEntity;
import alten.datasource.mappers.IUserEntityMapper;
import alten.datasource.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserAdapter implements IUserPort {

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.map(this::fromEntity);
    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity userEntity = toEntity(user);
        UserEntity savedEntity = userRepository.save(userEntity);
        return fromEntity(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(this::fromEntity);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private User fromEntity(UserEntity userEntity) {
        return modelMapper.map(userEntity, User.class);
    }

    private UserEntity toEntity(User user) {
        return modelMapper.map(user, UserEntity.class);
    }
}
