package org.nikita.user.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nikita.user.dto.UserDto;
import org.nikita.user.entity.User;
import org.nikita.user.exceptions.UserAlreadyHasTheSameEmailException;
import org.nikita.user.exceptions.UserAlreadyHasTheSamePhoneNumberException;
import org.nikita.user.exceptions.UserNotAdultException;
import org.nikita.user.exceptions.UserNotFoundException;
import org.nikita.user.repository.UserRepository;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final static Long ID = 1L;
    private final static String EMAIL = "email@gmail.com";
    private final static String FIRST_NAME = "name";
    private final static String LAST_NAME = "surname";
    private final static LocalDate BIRTHDAY = LocalDate.of(2000, 8, 20);
    private final static String ADDRESS = "address";
    private final static String PHONE_NUMBER = "099-999-99-99";
    private static User user;
    private static ArrayList<User> list;
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService service;

    @BeforeAll
    static void before() {
        user = new User(ID, EMAIL, FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS, PHONE_NUMBER);
        list = new ArrayList<>();
        list.add(user);
    }

    @Test
    void getAllUsersTest() {
        when(repository.findAll()).thenReturn(list);
        ArrayList<User> result = (ArrayList<User>) service.getAllUsers();

        assertEquals(1, result.size());
        assertTrue(Objects.deepEquals(list.getFirst(), result.getFirst()));
    }

    @Test
    void getUserByIdCorrectTest() {
        long id = user.getId();

        when(repository.findById(id)).thenReturn(Optional.of(user));
        User foundUser = service.getUserById(id);

        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getPhoneNumber(), foundUser.getPhoneNumber());
        assertTrue(Objects.deepEquals(user, foundUser));
    }

    @Test
    void getUserByIdIncorrectTest() {
        long id = -1;

        when(repository.findById(id)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> service.getUserById(id));
    }

    @Test
    void deleteUserByIdCorrectTest() {
        long id = user.getId();
        ArrayList<User> tmp = new ArrayList<>();
        tmp.add(user);

        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);
        service.deleteUserById(id);
        tmp.removeFirst();

        assertEquals(0, tmp.size());
    }

    @Test
    void deleteUserByIdIncorrectTest() {
        long id = -1;

        assertThrows(UserNotFoundException.class, () -> service.deleteUserById(id));
    }

    @Test
    void createNewUserCorrectTest() throws Exception {
        setAgeDefaultAtService();

        when(repository.save(user)).thenReturn(user);
        User result = service.createNewUser(user);

        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getBirthday(), result.getBirthday());
        assertEquals(user.getAddress(), result.getAddress());
        assertEquals(user.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    void createNewUserWithIncorrectAgeTest() throws Exception {
        setAgeDefaultAtService();
        user.setBirthday(LocalDate.now());

        assertThrows(UserNotAdultException.class, () -> service.createNewUser(user));
    }

    @Test
    void createNewUserWithExistEmailTest() throws Exception {
        setAgeDefaultAtService();
        String email = user.getEmail();

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyHasTheSameEmailException.class, () -> service.createNewUser(user));
    }

    @Test
    void createNewUserWithExistPhoneNumberTest() throws Exception {
        setAgeDefaultAtService();
        String phoneNumber = user.getPhoneNumber();

        when(repository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyHasTheSamePhoneNumberException.class, () -> service.createNewUser(user));
    }

    @Test
    void getAllUsersBetweenFromAndToTest() {
        LocalDate from = LocalDate.of(2000, 7, 1);
        LocalDate to = LocalDate.of(2000, 9, 1);

        when(repository.findByBirthdayBetweenFromAndTo(from, to)).thenReturn(list);
        ArrayList<User> result = (ArrayList<User>) service.getAllUsersBetweenFromAndTo(from, to);

        assertEquals(list.size(), result.size());
        assertEquals(list, result);
        assertEquals(list.getFirst(), result.getFirst());
    }

    @Test
    void updateUserByIdCorrectTest() {
        long id = user.getId();
        String newEmail = "newEmail@gmail.com";
        UserDto userDto = new UserDto(newEmail, null, null, null, null, null);

        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);
        User result = service.updateUserById(id, userDto);

        assertEquals(newEmail, result.getEmail());
        assertEquals(FIRST_NAME, result.getFirstName());
    }

    @Test
    void updateUserByIdWithIncorrectAgeTest() throws Exception {
        setAgeDefaultAtService();
        long id = user.getId();
        UserDto userDto = new UserDto(null, null, null, LocalDate.now(), null, null);

        when(repository.findById(id)).thenReturn(Optional.of(user));

        assertThrows(UserNotAdultException.class, () -> service.updateUserById(id, userDto));
    }

    @Test
    void updateUserByIdWithExistEmailTest() {
        long id = user.getId();
        UserDto userDto = new UserDto("incorrectEmail@gmail.com", null, null, null, null, null);

        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(repository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyHasTheSameEmailException.class, () -> service.updateUserById(id, userDto));
    }

    @Test
    void updateUserByIdWithExistPhoneNumber() {
        long id = user.getId();
        UserDto userDto = new UserDto(null, null, null, null, null, "099-099-99-00");

        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(repository.findByPhoneNumber(userDto.getPhoneNumber())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyHasTheSamePhoneNumberException.class, () -> service.updateUserById(id, userDto));
    }

    private void setAgeDefaultAtService() throws Exception {
        Field age = UserService.class.getDeclaredField("age");
        age.setAccessible(true);
        age.set(service, 18);
        age.setAccessible(false);
    }


}