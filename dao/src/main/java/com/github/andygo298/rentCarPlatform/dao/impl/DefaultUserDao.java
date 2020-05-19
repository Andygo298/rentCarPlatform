package com.github.andygo298.rentCarPlatform.dao.impl;


import com.github.andygo298.rentCarPlatform.dao.utils.SFUtil;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.model.User;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

public class DefaultUserDao implements UserDao {

    private static class SingletonHolder {
        static final UserDao HOLDER_INSTANCE = new DefaultUserDao();
    }

    public static UserDao getInstance() {
        return DefaultUserDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<User> getUsers() {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            TypedQuery<User> query = session.createQuery("from User", User.class)
                    .setCacheable(true);
            List<User> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList;
        }
    }

    @Override
    public long save(User user) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user);
            long id = user.getId();
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    @Override
    public User getUserById(Long id) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            User getUser = session.get(User.class, id);
            session.getTransaction().commit();
            return getUser;
        }
    }
}
