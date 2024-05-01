package org.nikita.user.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Nikita Gladkov
 * @version 1.0
 */
public class UserTest {

    private final static Long ID = 1L;
    private final static String EMAIL = "email@gmail.com";
    private final static String FIRST_NAME = "name";
    private final static String LAST_NAME = "surname";
    private final static LocalDate BIRTHDAY = LocalDate.of(2000, 8, 20);
    private final static String ADDRESS = "address";
    private final static String PHONE_NUMBER = "099-999-99-99";

    @Test
    public void createUser() {
        User user = new User(ID, EMAIL, FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS, PHONE_NUMBER);

        assertEquals(user.getId(), ID);
        assertEquals(user.getEmail(), EMAIL);
        assertEquals(user.getFirstName(), FIRST_NAME);
        assertEquals(user.getLastName(), LAST_NAME);
        assertEquals(user.getBirthday(), BIRTHDAY);
        assertEquals(user.getAddress(), ADDRESS);
        assertEquals(user.getPhoneNumber(), PHONE_NUMBER);
    }


}