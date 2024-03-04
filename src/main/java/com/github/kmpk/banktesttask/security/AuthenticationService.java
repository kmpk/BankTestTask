package com.github.kmpk.banktesttask.security;

import com.github.kmpk.banktesttask.to.AuthResponseTo;
import com.github.kmpk.banktesttask.to.AuthRequestTo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseTo signIn(AuthRequestTo request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.login(), request.password()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.login());
        return new AuthResponseTo(jwtService.generateToken(userDetails));
    }
}
