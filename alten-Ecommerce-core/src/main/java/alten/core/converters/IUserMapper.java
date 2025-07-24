package alten.core.converters;
import alten.core.dtos.user.UserDTO;
import alten.core.dtos.user.UserUpdateDTO;
import alten.core.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserUpdateDTO dto);
    User toEntity(UserDTO userDTO);
}