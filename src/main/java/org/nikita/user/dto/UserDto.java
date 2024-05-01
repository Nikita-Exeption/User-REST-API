package org.nikita.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class UserDto {

    @Email(message = "Email should have valid value")
    private String email;

    @Size(min = 2, max = 50, message = "First name should have more than 2 and less than 50 letter")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name should have more than 2 and less than 50 letter")
    private String lastName;

    @NotNull(message = "Birthday don't should empty")
    private LocalDate birthday;

    private String address;

    @Pattern(regexp = "^[0-9]{0,3}-[0-9]{0,3}-[0-9]{0,2}-[0-9]{0,2}$",
    message = "Phone number should be correct, for example: 999-999-99-99")
    private String phoneNumber;
}
