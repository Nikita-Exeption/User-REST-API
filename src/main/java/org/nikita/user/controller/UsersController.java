package org.nikita.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nikita.user.dto.UserDto;
import org.nikita.user.dto.UserMapper;
import org.nikita.user.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<UserDto> getAllUsers(@RequestParam(value = "from", required = false)
                                         @DateTimeFormat LocalDate from,
                                         @RequestParam(value = "to", required = false)
                                         @DateTimeFormat LocalDate to) {
        if (from != null && to != null) {
            log.info("CONTROLLER: Received all users from:{} - to:{}", from, to);
            return mapper.usersToUsersDto(service.getAllUsersBetweenFromAndTo(from, to));
        }
        log.info("CONTROLLER: Received all users");
        return mapper.usersToUsersDto(service.getAllUsers());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createNewUser(@RequestBody @Valid UserDto user, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            log.info("CONTROLLER: Mistakes in fields");
            throw new BindException(result);
        } else {
            log.info("CONTROLLER: User{} - created", mapper.userDtoToUser(user));
            return mapper.userToUserDto(service.createNewUser(mapper.userDtoToUser(user)));
        }
    }
}
