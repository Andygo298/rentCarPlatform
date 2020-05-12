package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultAuthUserDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultUserDao;
import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {

    private static class SingletonHolder {
        static final UserService HOLDER_INSTANCE = new DefaultUserService();
    }
    public static UserService getInstance() {
        return DefaultUserService.SingletonHolder.HOLDER_INSTANCE;
    }

    private UserDao userDao = DefaultUserDao.getInstance();

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public long saveUsers(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUserById(Long id) {
       return userDao.getUserById(id);
    }
}
