package com.github.andygo298.rentCarPlatform.service;

import com.github.andygo298.rentCarPlatform.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    long saveUsers(User user);
    User getUserById(Long id);
}
