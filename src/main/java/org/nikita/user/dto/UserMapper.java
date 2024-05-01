package org.nikita.user.dto;

import org.mapstruct.Mapper;
import org.nikita.user.entity.User;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    Iterable<UserDto> usersToUsersDto(Iterable<User> users);

    UserDto userToUserDto(User user);


    User userDtoToUser(UserDto userDto);

}
