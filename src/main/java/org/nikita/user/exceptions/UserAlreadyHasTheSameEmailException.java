package org.nikita.user.exceptions;

import lombok.extern.slf4j.Slf4j;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@Slf4j
public class UserAlreadyHasTheSameEmailException extends RuntimeException {


    public UserAlreadyHasTheSameEmailException(String email) {
        super("User with this email: " + email + " already exist");
        log.error("SERVICE: User have the same email {} as another user", email);
    }
}
