package com.example.restaurant.service.interfaces;

import com.example.restaurant.model.dto.RegisterUserRequest;

public interface IUserService {
    void createCustomer(RegisterUserRequest request);
    void createAdmin(RegisterUserRequest request);
}
