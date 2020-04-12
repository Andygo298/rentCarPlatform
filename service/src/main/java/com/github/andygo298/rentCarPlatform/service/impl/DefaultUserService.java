package com.github.andygo298.rentCarPlatform.service.impl;

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

    @Override
    public List<User> getUsers() {
        return DefaultUserDao.getInstance().getUsers();
    }

    @Override
    public long saveUsers(User user) {
        return DefaultUserDao.getInstance().save(user);
    }

    @Override
    public void saveAuthUser(AuthUser authUser) {
       DefaultAuthUserDao.getInstance().saveAuthUser(authUser);
    }

    @Override
    public User getUserById(Long id) {
       return DefaultUserDao.getInstance().getUserById(id);
    }
}
