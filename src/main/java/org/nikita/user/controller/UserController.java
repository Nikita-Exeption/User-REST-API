package org.nikita.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nikita.user.dto.UserDto;
import org.nikita.user.dto.UserMapper;
import org.nikita.user.entity.User;
import org.nikita.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/users/{user_id:\\d+}")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @ModelAttribute
    public User findUserById(@PathVariable("user_id") Long userId) {
        log.info("CONTROLLER: Got model of user by id:{}", userId);
        return service.getUserById(userId);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUserById(@RequestBody @Valid UserDto userDto, @ModelAttribute User user) {
        log.info("CONTROLLER: Updated user:{} by id:{}, new value of user:{}", user, user.getId(), userDto);
        return mapper.userToUserDto(service.updateUserById(user.getId(), userDto));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@ModelAttribute User user) {
        log.info("CONTROLLER: Deleted user by id:{}", user.getId());
        service.deleteUserById(user.getId());
    }


}
