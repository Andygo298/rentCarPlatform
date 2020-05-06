package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.AuthUserDao;
import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.model.AuthUser;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;

public class DefaultAuthUserDao implements AuthUserDao {

    private static final Logger log = LoggerFactory.getLogger(DefaultAuthUserDao.class);

    private static class SingletonHolder {
        static final AuthUserDao HOLDER_INSTANCE = new DefaultAuthUserDao();
    }

    public static AuthUserDao getInstance() {
        return DefaultAuthUserDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public AuthUser getByLogin(String login) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from AuthUser au where au.login in :userlog");
            query.setParameter("userlog", login);
            Object getAuthUser = query.getSingleResult();
            session.getTransaction().commit();
            session.close();
            return (AuthUser) getAuthUser;
        } catch (RuntimeException e) {
            return null;
        }
    }


    @Override
    public long saveAuthUser(AuthUser user) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user);
            long id = user.getId();
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }
}
