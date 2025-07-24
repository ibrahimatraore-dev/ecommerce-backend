package alten.datasource.mappers;

import alten.core.entities.User;
import alten.datasource.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {

    User toSimpleUser(UserEntity user);
}
