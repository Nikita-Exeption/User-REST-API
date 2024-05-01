package org.nikita.user.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nikita.user.dto.UserDto;
import org.nikita.user.dto.UserMapper;
import org.nikita.user.entity.User;
import org.nikita.user.exceptions.UserNotFoundException;
import org.nikita.user.service.UserService;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService service;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserController controller;

    private static UserDto userDto;
    private static User user;

    private final static Long ID = 1L;
    private final static String EMAIL = "email@gmail.com";
    private final static String FIRST_NAME = "name";
    private final static String LAST_NAME = "surname";
    private final static LocalDate BIRTHDAY = LocalDate.of(2000, 8, 20);
    private final static String ADDRESS = "address";
    private final static String PHONE_NUMBER = "099-999-99-99";


    @BeforeAll
    static void before() {
        user = new User(ID, EMAIL, FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS, PHONE_NUMBER);
        userDto = new UserDto(EMAIL, FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS, PHONE_NUMBER);
    }

    @Test
    void findByIdTestExistsUserTest() {
        long id = user.getId();

        when(service.getUserById(id)).thenReturn(user);
        User foundUser = controller.findUserById(id);

        assertEquals(user.getId(), foundUser.getId());
        assertTrue(checkAllFields(user, foundUser));
    }

    @Test
    void findByIdNotExistUserTest() {
        long id = -1;

        when(service.getUserById(id)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> controller.findUserById(id));
    }

    @Test
    void deleteUserByIdTest(){
        long id = user.getId();
        ArrayList<User> list = new ArrayList<>();
        list.add(user);

        doNothing().when(service).deleteUserById(id);
        list.remove(user);
        controller.deleteUserById(user);

        assertEquals(0, list.size());
    }

    @Test
    void updateUserByIdTest(){
        long id = user.getId();
        String newEmail = "newEmail@gmail.com";
        UserDto newUser = new UserDto(newEmail, FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS, PHONE_NUMBER);

        when(mapper.userToUserDto(service.updateUserById(id, newUser))).thenReturn(newUser);
        UserDto updatedUser = controller.updateUserById(userDto, user);

        assertTrue(checkAllFields(newUser, updatedUser));
    }

    private boolean checkAllFields(UserDto expect, UserDto result){
        return Objects.deepEquals(expect, result);
    }

    private boolean checkAllFields(User expect, User result){
        return Objects.deepEquals(expect, result);
    }

}
