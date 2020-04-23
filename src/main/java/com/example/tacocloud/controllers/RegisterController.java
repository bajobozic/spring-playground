package com.example.tacocloud.controllers;

import com.example.tacocloud.models.RegisterForm;
import com.example.tacocloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/register")
public class RegisterController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    String registerForm() {
        return "registration";
    }

    @PostMapping
    String saveRegisterForm(@Valid RegisterForm registerForm, Errors errors) {
        if (errors.hasErrors())
            return "homePage";
        userRepository.save(registerForm.toUser(passwordEncoder));
        return "redirect:/design";
    }
}
