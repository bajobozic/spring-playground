package com.example.tacocloud.security.providers;


import com.example.tacocloud.security.authentication.UsernamePasswordAuthentication;
import com.example.tacocloud.security.services.SecureUserDetails;
import com.example.tacocloud.security.services.SecureUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private SecureUserDetailsService secureUserDetailsService;
    private PasswordEncoder passwordEncoder;

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
        throw new BadCredentialsException("Bad credentials error");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(UsernamePasswordAuthentication.class);
    }
}
