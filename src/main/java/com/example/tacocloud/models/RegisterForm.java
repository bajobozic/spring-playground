package com.example.tacocloud.models;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {
    @NotBlank(message = "Username must not be empty")
    private String username;
    @NotBlank
    @Size(min = 8, max = 16, message = "Passwordmust be at least 8 charaters long")
    private String password;
    @NotBlank
    private String confirmation;
    private String fullname;
    private String street;
    private String city;
    private String zip;
    private String state;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), fullname, street, city, zip, state, phone);
    }
}
