package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.model.AuthUser;

public interface AuthUserDao {

    AuthUser getByLogin(String login);

    long saveAuthUser(AuthUser user);
}
