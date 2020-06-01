package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.AuthUserDao;
import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.enums.Role;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DefaultSecurityServiceTest {
    @Mock
    AuthUserDao authUserDao;

    @InjectMocks
    DefaultSecurityService securityService;

    @Test
    void getInstanceTest() {
        SecurityService instance = DefaultSecurityService.getInstance();
        assertNotNull(instance);
    }

    @Test
    void testLoginNotExist() {
        when(authUserDao.getByLogin("admin")).thenReturn(null);
        AuthUser login = securityService.login("admin", "admin");
        assertNull(login);
    }

    @Test
    void testLoginCorrect() {
        when(authUserDao.getByLogin("admin")).thenReturn(new AuthUser(null, "admin", "pass", null, (User) null));
        AuthUser userFromDb = securityService.login("admin", "pass");
        assertNotNull(userFromDb);
        assertEquals(userFromDb.getLogin(), "admin");
        assertNotNull(userFromDb.getPassword(), "pass");
    }

    @Test
    void testLoginWrongPass() {
        when(authUserDao.getByLogin("admin")).thenReturn(new AuthUser(null, "admin", "pass", null, (User) null));
        AuthUser login = securityService.login("admin", "pass2");
        assertNull(login);
    }

    @Test
    void testSaveAuthUser(){
        User mockUser = new User(1L,"Petr","Petrov","petr@gmail.com",false);
        AuthUser mockAuthUser = new AuthUser(1L,"user","user", Role.USER,mockUser);

        when(authUserDao.saveAuthUser(mockAuthUser)).thenReturn(1L);
        Long id = securityService.saveAuthUser(mockAuthUser);
        assertEquals((Object) 1L,id);
    }
}
