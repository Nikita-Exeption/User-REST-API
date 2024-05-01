package org.nikita.user.exceptions;

import lombok.extern.slf4j.Slf4j;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@Slf4j
public class UserPhoneNotCorrectException extends RuntimeException {

    public UserPhoneNotCorrectException(String message){
        super("This phone number:" + " is not correct");
        log.error("SERVICE: user's phone number:{} already exists", message);
    }
}
