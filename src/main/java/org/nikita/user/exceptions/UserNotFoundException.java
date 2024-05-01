package org.nikita.user.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class UserNotFoundException extends RuntimeException {


    public UserNotFoundException(Long id) {
        super("User with ID: " + id + " is not found");
        log.error("SERVICE: User not found by id:{}", id);
    }

}
