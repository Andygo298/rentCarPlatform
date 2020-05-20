package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.AuthUserDao;
import com.github.andygo298.rentCarPlatform.dao.converter.AuthUserConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.AuthUserEntity;
import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.model.AuthUser;

import org.hibernate.NonUniqueResultException;
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
            Query query = session.createQuery("from AuthUserEntity au where au.login in :userLog");
            query.setParameter("userLog", login);
                AuthUserEntity getAuthUser;
            try {
                getAuthUser =(AuthUserEntity) query.getSingleResult();
            } catch (NonUniqueResultException e) {
                log.info("user not found by login{}", login);
                return null;
            }
            session.getTransaction().commit();
            session.close();
            AuthUser authUser = AuthUserConverter.fromEntity(getAuthUser);
            return authUser;
        }
    }


    @Override
    public long saveAuthUser(AuthUser user) {
        AuthUserEntity authUserEntity = AuthUserConverter.toEntity(user);
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(authUserEntity);
            long id = authUserEntity.getId();
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }
}
