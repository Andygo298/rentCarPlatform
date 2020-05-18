package com.github.andygo298.rentCarPlatform.service;

import com.github.andygo298.rentCarPlatform.model.AuthUser;

public interface SecurityService {
    Long saveAuthUser(AuthUser authUser);
    AuthUser login(String login, String password);
}
