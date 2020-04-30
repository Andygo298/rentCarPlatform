package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.dao.impl.DefaultAuthUserDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultUserDao;
import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.Role;
import com.github.andygo298.rentCarPlatform.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthUserDaoHiberTest {
    final UserDao userDao = DefaultUserDao.getInstance();
    final AuthUserDao authUserDao = DefaultAuthUserDao.getInstance();

    @Test
    public void saveAuthUserTest() {
        User user = new User(null, "testsaveAU", "testsaveAU", "gggAU@ggg.gmail", false);
        long saveUserId = userDao.save(user);
        AuthUser authUser = new AuthUser(null, "testLoginAU", "testPassAU", Role.USER, user);
        Session session = SFUtil.getSession();
        session.beginTransaction();
        session.save(authUser);
        session.getTransaction().commit();

        AuthUser authUserFromDb = session.get(AuthUser.class, authUser.getId());
        assertEquals(authUser, authUserFromDb);

        session.beginTransaction();
        session.delete(authUserFromDb);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
}