package com.example.tacocloud.security.providers;


import com.example.tacocloud.security.authentication.UsernamePasswordAuthentication;
import com.example.tacocloud.security.services.SecureUserDetails;
import com.example.tacocloud.security.services.SecureUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private SecureUserDetailsService secureUserDetailsService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsernamePasswordAuthenticationProvider(SecureUserDetailsService secureUserDetailsService, PasswordEncoder passwordEncoder) {
        this.secureUserDetailsService = secureUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        SecureUserDetails userDetails = (SecureUserDetails) secureUserDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword()))
            return new UsernamePasswordAuthentication(null, userDetails.getUsername(), userDetails.getAuthorities());
        else
            throw new RuntimeException("Provider error");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthentication.class.equals(aClass) || UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
