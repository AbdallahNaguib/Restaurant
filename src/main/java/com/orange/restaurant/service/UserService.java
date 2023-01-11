package com.orange.restaurant.service;

import com.orange.restaurant.Constants;
import com.orange.restaurant.error.ConfirmPasswordNotSameAsOriginalPassException;
import com.orange.restaurant.error.EmailAlreadyExistsException;
import com.orange.restaurant.model.User;
import com.orange.restaurant.model.dto.RegisterUserRequest;
import com.orange.restaurant.repository.UserRepo;
import com.orange.restaurant.security.SecurityContextService;
import com.orange.restaurant.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, IUserService {
    private final UserRepo repo;
    private final PasswordEncoder bcryptEncoder;
    private final SecurityContextService securityContextService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repo.findUserByEmail(username);
    }

    private void createUser(RegisterUserRequest request,String role){
        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new ConfirmPasswordNotSameAsOriginalPassException();
        }
        if (loadUserByUsername(request.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(bcryptEncoder.encode(request.getPassword()))
                .mobileNumber(request.getMobileNumber())
                .userType(role)
                .name(request.getName())
                .build();
        repo.save(user);
    }
    @Override
    public void createCustomer(RegisterUserRequest request) {
        createUser(request,Constants.CUSTOMER);
    }

    @Override
    public void createAdmin(RegisterUserRequest request) {
        createUser(request,Constants.ADMIN);
    }
}
