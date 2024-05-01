package org.nikita.user.exceptions;

import lombok.extern.slf4j.Slf4j;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@Slf4j
public class UserNotAdultException extends RuntimeException {

    public UserNotAdultException(){
        super("Not enough years old");
        log.error("SERVICE: User doesn't have enough years old");
    }

}
