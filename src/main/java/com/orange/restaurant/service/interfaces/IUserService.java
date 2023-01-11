package com.orange.restaurant.service.interfaces;

import com.orange.restaurant.model.dto.RegisterUserRequest;

public interface IUserService {
    void createCustomer(RegisterUserRequest request);
    void createAdmin(RegisterUserRequest request);
}
