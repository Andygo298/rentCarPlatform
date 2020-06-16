package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.UserDao;

import com.github.andygo298.rentCarPlatform.dao.impl.DefaultUserDao;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.SecurityService;
import com.github.andygo298.rentCarPlatform.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @Mock
    UserDao defaultUserDao;

    @InjectMocks
    DefaultUserService defaultUserService;


    @Test
        void testUserById() {
        User mockUser = new User(2L,"Ivan","Ivanov","iva@gmail.com",false);
        when(defaultUserDao.getUserById((long) 2)).thenReturn(mockUser);
        User user = defaultUserService.getUserById(2L);
        assertEquals(mockUser.getId(),user.getId());
    }

    @Test
    void testUserNotExist(){
        Long mockId = 4L;
        when(defaultUserDao.getUserById(mockId)).thenReturn(null);
        User user = defaultUserService.getUserById(mockId);

    }

    @Test
    void testSaveUser(){
        User mockUser = new User(null,"Petr","Petrov","petr@gmail.com",false);
        when(defaultUserDao.save(mockUser)).thenReturn(1L);

        Long saveUserId = defaultUserService.saveUsers(mockUser);
        assertNotNull(saveUserId);
        assertEquals((Object) 1L, saveUserId);
    }



    @Test
    void testUsers(){
        User user1 = new User(null,"Petr","Petrov","petr@gmail.com",false);
        User user2 = new User(null,"Petr","Petrov","petr@gmail.com",false);
        List<User> users = Arrays.asList(user1, user2);
        when(defaultUserDao.getUsers()).thenReturn(users);

        List<User> usersFromDb = defaultUserService.getUsers();
        assertEquals(users, usersFromDb);
    }
    @Test
    void testUsersNotExist(){
        when(defaultUserDao.getUsers()).thenReturn(null);
        List<User> usersFromDb = defaultUserService.getUsers();
        assertNull(usersFromDb);
    }

}
