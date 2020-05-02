package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.DefaultAuthUserDao;
import com.github.andygo298.rentCarPlatform.dao.DefaultUserDao;
import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {

    private UserDao instance = DefaultUserDao.getInstance();

    private static class SingletonHolder {
        static final UserService HOLDER_INSTANCE = new DefaultUserService();
    }

    public static UserService getInstance() {
        return DefaultUserService.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<User> getUsers() {
        return instance.getUsers();
    }

    @Override
    public long saveUsers(User user) {
        return instance.save(user);
    }

    @Override
    public void saveAuthUser(AuthUser authUser) {
       DefaultAuthUserDao.getInstance().saveAuthUser(authUser);
    }

    @Override
    public User getUserById(Long id) {
       return instance.getUserById(id);
    }
}
