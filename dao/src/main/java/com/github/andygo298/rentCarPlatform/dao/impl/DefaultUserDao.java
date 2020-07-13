package com.github.andygo298.rentCarPlatform.dao.impl;


import com.github.andygo298.rentCarPlatform.dao.converter.UserConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.UserEntity;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.model.User;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultUserDao implements UserDao {
    private final SessionFactory sessionFactory;

    public DefaultUserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getUsers() {
        TypedQuery<UserEntity> query = sessionFactory.getCurrentSession()
                .createQuery("from UserEntity", UserEntity.class)
                .setCacheable(true);
        List<UserEntity> resultList = query.getResultList();
        return resultList.stream()
                .map(UserConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public long save(User user) {
        UserEntity userEntity = UserConverter.toEntity(user);
        sessionFactory.getCurrentSession().saveOrUpdate(userEntity);
        return userEntity.getId();
    }

    @Override
    public User getUserById(Long id) {
        UserEntity getUser = sessionFactory.getCurrentSession().get(UserEntity.class, id);
        return UserConverter.fromEntity(getUser);
    }
}
