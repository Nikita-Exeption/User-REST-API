package org.nikita.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nikita.user.dto.UserDto;
import org.nikita.user.entity.User;
import org.nikita.user.exceptions.*;
import org.nikita.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Value("${user.min.age}")
    private Integer age;

    private final static String REGEX = "^[0-9]{0,3}-[0-9]{0,3}-[0-9]{0,2}-[0-9]{0,2}$";

    @Transactional(readOnly = true)
    public Iterable<User> getAllUsers() {
        log.info("SERVICE: Find all Users");
        return repository.findAll();
    }

    @Transactional
    public User createNewUser(User user) {
        if (checkAge(user.getBirthday())) {
            if (checkEmail(user.getEmail())) {
                if (checkPhoneNumber(user.getPhoneNumber())) {
                    log.info("SERVICE: Create user {}", user);
                    return repository.save(user);
                } else {
                    throw new UserAlreadyHasTheSamePhoneNumberException(user.getPhoneNumber());
                }
            } else {
                throw new UserAlreadyHasTheSameEmailException(user.getEmail());
            }
        } else {
            throw new UserNotAdultException();
        }
    }


    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        log.info("SERVICE: Received user by ID:{}", userId);
        return repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional(readOnly = true)
    public Iterable<User> getAllUsersBetweenFromAndTo(LocalDate from, LocalDate to) {
        log.info("SERVICE: Received all users from {}, to{}", from, to);
        return repository.findByBirthdayBetweenFromAndTo(from, to);
    }

    @Transactional
    public User updateUserById(Long id, UserDto userDto) {
        User user = getUserById(id);
        log.info("SERVICE: Get user by id {}", id);
        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail()) && userDto.getEmail().matches("^.+@.+.com")) {
            if (checkEmail(userDto.getEmail())) {
                user.setEmail(userDto.getEmail());
            } else {
                throw new UserAlreadyHasTheSameEmailException(userDto.getEmail());
            }
        }
        if (userDto.getFirstName() != null && !userDto.getFirstName().equals(user.getFirstName()) && userDto.getFirstName().length() > 2) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null && !userDto.getLastName().equals(user.getLastName()) && userDto.getLastName().length() > 2) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getBirthday() != null && !userDto.getBirthday().equals(user.getBirthday())) {
            if (checkAge(userDto.getBirthday())) {
                user.setBirthday(userDto.getBirthday());
            } else {
                throw new UserNotAdultException();
            }
        }
        if (userDto.getAddress() != null && !userDto.getAddress().equals(user.getAddress()) && userDto.getAddress().length() > 2) {
            user.setAddress(userDto.getAddress());
        }
        if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().equals(user.getPhoneNumber())) {
            if (checkPhoneNumber(userDto.getPhoneNumber())) {
                if (userDto.getPhoneNumber().matches(REGEX)) {
                    user.setPhoneNumber(userDto.getPhoneNumber());
                } else {
                    throw new UserPhoneNotCorrectException(userDto.getPhoneNumber());
                }
            } else {
                throw new UserAlreadyHasTheSamePhoneNumberException(userDto.getPhoneNumber());
            }
        }
        repository.save(user);
        log.info("SERVICE: Updated user{} by Id:{}", user, id);
        return user;
    }


    @Transactional
    public void deleteUserById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("SERVICE: User deleted by id:{}", id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    private boolean checkAge(LocalDate birthday) {
        return birthday.isBefore(LocalDate.now().minusYears(age));
    }

    private boolean checkEmail(String email) {
        return repository.findByEmail(email).isEmpty();
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).isEmpty();
    }

}
