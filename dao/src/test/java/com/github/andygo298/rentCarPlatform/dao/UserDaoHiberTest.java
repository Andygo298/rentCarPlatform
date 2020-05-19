package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.dao.utils.SFUtil;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultUserDao;
import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.enums.Role;
import com.github.andygo298.rentCarPlatform.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoHiberTest {
    final UserDao userDao = DefaultUserDao.getInstance();

    @BeforeEach
    public void init() {
        Session session = SFUtil.getSession();
        session.beginTransaction();
        User user1 = new User(null, "TestName1", "TestLastName1", "test1@gmail.com", false);
        User user2 = new User(null, "TestName2", "TestLastName2", "test2@gmail.com", false);
        User user3 = new User(null, "TestName3", "TestLastName3", "test3@gmail.com", false);
        session.saveOrUpdate(user1);
        session.saveOrUpdate(user2);
        session.saveOrUpdate(user3);
        AuthUser authUser1 = new AuthUser(null, "TestLogin1", "TestPass1", Role.USER, user1);
        AuthUser authUser2 = new AuthUser(null, "TestLogin2", "TestPass2", Role.USER, user2);
        AuthUser authUser3 = new AuthUser(null, "TestLogin3", "TestPass3", Role.USER, user3);
        session.saveOrUpdate(authUser1);
        session.saveOrUpdate(authUser2);
        session.saveOrUpdate(authUser3);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void saveTest() {
            long saveId = userDao.save(new User(null, "Andrew", "lozouski", "andygo298@gmail.com", false));
            final User user = userDao.getUserById(saveId);
            assertNotNull(user);
            assertEquals(user.getFirstName(), "Andrew");
    }

    @Test
    public void getUserByIdTest() {
        User user = new User(null, "test1", "test2", "ggg@ggg.gmail", false);
        Long saveUserId = userDao.save(user);
        User userFromDb = userDao.getUserById(saveUserId);

        assertEquals(user.getId(), userFromDb.getId());
        assertEquals(user.getFirstName(), userFromDb.getFirstName());
        assertEquals(user.getLastName(), userFromDb.getLastName());

    }

    @Test
    public void getListUsersTest() {
        List<User> usersFromDb = userDao.getUsers();
        assertNotNull(usersFromDb);
        assertEquals(usersFromDb.get(0).getLastName(), usersFromDb.get(0).getLastName());
    }

    @AfterAll
    public static void cleanUp() {
        SFUtil.closeSessionFactory();
    }
}
