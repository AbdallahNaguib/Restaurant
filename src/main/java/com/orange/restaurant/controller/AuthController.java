package com.orange.restaurant.controller;

import com.orange.restaurant.model.dto.RegisterUserRequest;
import com.orange.restaurant.security.JwtRequest;
import com.orange.restaurant.security.JwtResponse;
import com.orange.restaurant.security.JwtTokenUtil;
import com.orange.restaurant.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    //Controllers should only do data mapping
    // then forward the execution to the service layer
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userDetailsService;

    //ToDo do you know that this could be done by spring security out of the box?
    @PostMapping(value = "/auth/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        //checkout using a token enhancer
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest user) {
        userDetailsService.createCustomer(user);
        return ResponseEntity.created(null).build();
    }

    //ToDo registering an admin should require special priv
    @PostMapping(value = "/admin/register_admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterUserRequest user) {
        userDetailsService.createAdmin(user);
        return ResponseEntity.created(null).build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
