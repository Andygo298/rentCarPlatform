package com.github.andygo298.rentCarPlatform.dao.impl;

import com.github.andygo298.rentCarPlatform.dao.AuthUserDao;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.converter.AuthUserConverter;
import com.github.andygo298.rentCarPlatform.dao.converter.UserConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.AuthUserEntity;
import com.github.andygo298.rentCarPlatform.dao.entity.UserEntity;
import com.github.andygo298.rentCarPlatform.model.AuthUser;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class DefaultAuthUserDao implements AuthUserDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultAuthUserDao.class);
    private final SessionFactory sessionFactory;
    private final UserDao userDao;

    public DefaultAuthUserDao(SessionFactory sessionFactory, UserDao userDao) {
        this.sessionFactory = sessionFactory;
        this.userDao = userDao;
    }

    @Override
    public AuthUser getByLogin(String login) {
        try {
            AuthUserEntity getAuthUser;
            Query query = sessionFactory.getCurrentSession().createQuery("from AuthUserEntity au where au.login in :userLogin");
            query.setParameter("userLogin", login);
            getAuthUser = (AuthUserEntity) query.getSingleResult();
            return AuthUserConverter.fromEntity(getAuthUser);
        } catch (NoResultException e) {
            log.info("---User not found by login: {}", login);
            return null;
        }
    }


    @Override
    public long saveAuthUser(AuthUser authUser) {
        AuthUserEntity authUserEntity = AuthUserConverter.toEntity(authUser);
        UserEntity userEntity = UserConverter.toEntity(userDao.getUserById(authUserEntity.getUserId()));
        authUserEntity.setUserEntity(userEntity);
        authUserEntity.setUserId(userEntity.getId());
        sessionFactory.getCurrentSession().saveOrUpdate(authUserEntity);
        return authUserEntity.getId();
    }
}
