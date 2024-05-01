package org.nikita.user.controller;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nikita.user.dto.UserDto;
import org.nikita.user.dto.UserMapper;
import org.nikita.user.service.UserService;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Test for UsersController")
public class UsersControllerTest {

    private final static String EMAIL = "email@gmail.com";
    private final static String FIRST_NAME = "name";
    private final static String LAST_NAME = "surname";
    private final static LocalDate BIRTHDAY = LocalDate.of(2000, 8, 20);
    private final static String ADDRESS = "address";
    private final static String PHONE_NUMBER = "099-999-99-99";
    private static UserDto userDto;
    private static Iterable<UserDto> list;
    @Mock
    private UserMapper mapper;
    @Mock
    private UserService service;
    @InjectMocks
    private UsersController controller;

    @BeforeAll
    static void before() {
        userDto = new UserDto(EMAIL, FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS, PHONE_NUMBER);
        list = List.of(userDto);
    }


    @Test
    void getAllUsersTest() {
        when(mapper.usersToUsersDto(service.getAllUsers())).thenReturn(list);
        Iterable<UserDto> result = controller.getAllUsers(null, null);

        assertEquals(list, result);
    }

    @Test
    void getAllUsersFromAndToDate() {
        LocalDate from = userDto.getBirthday().minusMonths(1);
        LocalDate to = userDto.getBirthday().plusMonths(1);

        when(mapper.usersToUsersDto(service.getAllUsersBetweenFromAndTo(from, to))).thenReturn(list);
        Iterable<UserDto> result = controller.getAllUsers(from, to);

        assertEquals(list, result);
        assertEquals(list.iterator().next().getEmail(), result.iterator().next().getEmail());
    }

    @Test
    void createUserTest() throws Exception {
        when(mapper.userToUserDto(service.createNewUser(mapper.userDtoToUser(userDto)))).thenReturn(userDto);
        UserDto createdUser = controller.createNewUser(userDto, mock(BindingResult.class));

        assertEquals(createdUser.getEmail(), userDto.getEmail());
        assertEquals(createdUser.getFirstName(), userDto.getFirstName());
        assertEquals(createdUser.getLastName(), userDto.getLastName());
        assertEquals(createdUser.getBirthday(), userDto.getBirthday());
        assertEquals(createdUser.getPhoneNumber(), userDto.getPhoneNumber());
        assertEquals(createdUser.getAddress(), userDto.getAddress());
    }

}
