package org.nikita.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.nikita.user.exceptions.UserAlreadyHasTheSameEmailException;
import org.nikita.user.exceptions.UserAlreadyHasTheSamePhoneNumberException;
import org.nikita.user.exceptions.UserNotAdultException;
import org.nikita.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, List<String>>> handleUserInvalidFields(BindException exception) {
        log.info("Handled BindException with message:{}", exception.getMessage());
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage).toList();
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyHasTheSameEmailException.class)
    public ResponseEntity<Map<String, List<String>>> handlerUserAlreadyHasTheSameEmail(UserAlreadyHasTheSameEmailException exception) {
        log.info("Handled UserAlreadyHasTheSameEmailException with message:{}", exception.getMessage());
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyHasTheSamePhoneNumberException.class)
    public ResponseEntity<Map<String, List<String>>> handlerUserAlreadyHasThePhoneNumber(UserAlreadyHasTheSamePhoneNumberException exception) {
        log.info("Handled UserAlreadyHasTheSamePhoneNumberException with message:{}", exception.getMessage());
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAdultException.class)
    public ResponseEntity<Map<String, List<String>>> handlerUserNotAdultException(UserNotAdultException exception) {
        log.info("Handled UserNotAdultException with message:{}", exception.getMessage());
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handlerUserNotFoundException(UserNotFoundException exception) {
        log.info("Handled UserNotFoundException with message:{}", exception.getMessage());
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, List<String>>> handlerOtherExceptions(Exception exception) {
        log.info("Handled other exceptions with message:{}", exception.getMessage());
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}
