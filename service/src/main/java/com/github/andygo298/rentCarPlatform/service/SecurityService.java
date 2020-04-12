package com.github.andygo298.rentCarPlatform.service;

import com.github.andygo298.rentCarPlatform.model.AuthUser;

public interface SecurityService {
    AuthUser login(String login, String password);
}
