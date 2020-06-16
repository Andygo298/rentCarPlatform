package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultUserService implements UserService {

    private final UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    @Transactional
    public long saveUsers(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
       return userDao.getUserById(id);
    }
}
