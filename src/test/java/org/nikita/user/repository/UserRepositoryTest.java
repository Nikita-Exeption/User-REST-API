package org.nikita.user.repository;

import org.junit.jupiter.api.Test;
import org.nikita.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@Sql("/sql/users.sql")
@Transactional
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;


    @Test
    void findByBirthdayBetweenFromAndToCorrectTest() {
        LocalDate from = LocalDate.of(2003, 1, 1);
        LocalDate to = LocalDate.of(2005, 1, 1);

        ArrayList<User> list = (ArrayList<User>) repository.findByBirthdayBetweenFromAndTo(from, to);

        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void findByBirthdayBetweenFromAndToIncorrectTest() {
        LocalDate from = LocalDate.of(1200, 1, 1);
        LocalDate to = LocalDate.of(1300, 1, 1);

        ArrayList<User> list = (ArrayList<User>) repository.findByBirthdayBetweenFromAndTo(from, to);

        assertEquals(0, list.size());
    }

    @Test
    void findByEmailCorrectTest() {
        String email = "email@gmail.com";

        Optional<User> user = repository.findByEmail(email);

        assertNotNull(user);
        assertNotEquals(Optional.empty(), user);
    }

    @Test
    void findByEmailIncorrectTest() {
        String email = "incorrect";

        Optional<User> result = repository.findByEmail(email);

        assertEquals(Optional.empty(), result);
    }

    @Test
    void findByPhoneNumberCorrectTest() {
        String phone = "099-000-00-00";

        Optional<User> result = repository.findByPhoneNumber(phone);

        assertNotEquals(Optional.empty(), result);
    }

    @Test
    void findByPhoneNumberIncorrectTest() {
        String phone = "Incorrect";

        Optional<User> result = repository.findByPhoneNumber(phone);

        assertEquals(Optional.empty(), result);
    }
}