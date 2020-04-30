package com.github.andygo298.rentCarPlatform.dao;

import com.github.andygo298.rentCarPlatform.dao.impl.DefaultUserDao;
import com.github.andygo298.rentCarPlatform.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoHiberTest {
    final UserDao userDao = DefaultUserDao.getInstance();

    @Test
    public void saveTest() {
        try (Session session = SFUtil.getSession()) {
            User user = new User(null, "testsave", "testsave", "ggg@ggg.gmail", false);
            session.beginTransaction();
            session.save(user);
            User userFromDb = session.get(User.class, user.getId());
            assertEquals(user, userFromDb);
            session.delete(userFromDb);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Test
    public void getUserTest() {
        User user = new User(null, "test1", "test2", "ggg@ggg.gmail", false);
        Long saveUserId = userDao.save(user);
        Session session = SFUtil.getSession();
        session.beginTransaction();
        user.setId(saveUserId);
        User userActual = userDao.getUserById(saveUserId);
        assertEquals(user.getId(),userActual.getId());
        assertEquals(user.getFirstName(), userActual.getFirstName());
        assertEquals(user.getLastName(), userActual.getLastName());
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void getListUsersTest() {
        Session session = SFUtil.getSession();
        session.beginTransaction();
        TypedQuery<User> query = session.createQuery("from User", User.class);
        List<User> resultList = query.getResultList();
        List<User> usersFromDb = userDao.getUsers();
        assertEquals(resultList.size(),usersFromDb.size());
        assertEquals(usersFromDb.get(resultList.size()-1).getLastName(), resultList.get(resultList.size() - 1).getLastName());
        session.getTransaction().commit();
        session.close();
    }
}
