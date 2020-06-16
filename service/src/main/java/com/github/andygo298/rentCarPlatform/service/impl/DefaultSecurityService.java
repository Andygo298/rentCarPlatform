package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.AuthUserDao;
import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.service.SecurityService;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSecurityService implements SecurityService {

    private final AuthUserDao authUserDao;

    public DefaultSecurityService(AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
    }

    @Override
    @Transactional
    public Long saveAuthUser(AuthUser authUser) {
        return authUserDao.saveAuthUser(authUser);
    }

    @Override
    @Transactional
    public AuthUser login(String login, String password) {
        AuthUser user = authUserDao.getByLogin(login);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
