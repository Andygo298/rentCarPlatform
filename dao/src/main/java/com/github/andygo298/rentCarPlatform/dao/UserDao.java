package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    long save(User user);

    User getUserById(Long id);
}
