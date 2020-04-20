package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.AuthUserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthUserSaveTest {

    @Mock
    AuthUserDao authUserDao;

    @InjectMocks
    DefaultUserService defaultUserService;

    @Test
    void saveAuthUserTest(){

    }
}
