package com.github.andygo298.rentCarPlatform.service.impl;

import com.github.andygo298.rentCarPlatform.dao.impl.DefaultUserDao;
import com.github.andygo298.rentCarPlatform.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultUserServiceTest {

    @Mock
    DefaultUserDao defaultUserDao;

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
    void testSaveUser(){
        User mockUser = new User(null,"Petr","Petrov","petr@gmail.com",false);
        Long saveUserId = defaultUserService.saveUsers(mockUser);
        when(defaultUserDao.save(mockUser)).thenReturn(saveUserId);
        assertNotNull(saveUserId);
    }
    @Test
    void testUsers(){
        List<User> usersList = defaultUserService.getUsers();
        User user = usersList.get(2);
        when(defaultUserDao.getUsers().get(2).getId()).thenReturn(user.getId());

    }

}
