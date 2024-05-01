package org.nikita.user.exceptions;

import lombok.extern.slf4j.Slf4j;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@Slf4j
public class UserAlreadyHasTheSamePhoneNumberException extends RuntimeException{

    public UserAlreadyHasTheSamePhoneNumberException(String message){
        super("User with phone number: " + message + " already exist");
        log.error("SERVICE: User have the same phone number {} as another user", message);
    }
}
