package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.AuthUserDao;
import com.github.andygo298.rentCarPlatform.dao.converter.AuthUserConverter;
import com.github.andygo298.rentCarPlatform.dao.converter.UserConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.AuthUserEntity;
import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.dao.entity.UserEntity;
import com.github.andygo298.rentCarPlatform.model.AuthUser;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
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
        try {
            AuthUserEntity getAuthUser;
            try (Session session = SFUtil.getSession()) {
                session.beginTransaction();
                Query query = session.createQuery("from AuthUserEntity au where au.login in :userLogin");
                query.setParameter("userLogin", login);
                getAuthUser = (AuthUserEntity) query.getSingleResult();
                session.getTransaction().commit();
            }
            return AuthUserConverter.fromEntity(getAuthUser);
        } catch (NoResultException e) {
            log.info("user not found by login{}", login);
            return null;
        }
    }


    @Override
    public long saveAuthUser(AuthUser authUser) {
        AuthUserEntity authUserEntity = AuthUserConverter.toEntity(authUser);
        UserEntity userEntity = UserConverter.toEntity(DefaultUserDao.getInstance().getUserById(authUserEntity.getUserId()));
        authUserEntity.setUserEntity(userEntity);
        authUserEntity.setUserId(userEntity.getId());
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(authUserEntity);
            long id = authUserEntity.getId();
            session.getTransaction().commit();
            return id;
        }
    }
}
