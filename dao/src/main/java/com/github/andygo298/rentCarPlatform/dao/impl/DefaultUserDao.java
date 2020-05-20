package com.github.andygo298.rentCarPlatform.dao.impl;


import com.github.andygo298.rentCarPlatform.dao.converter.UserConverter;
import com.github.andygo298.rentCarPlatform.dao.entity.UserEntity;
import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.model.User;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

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
            TypedQuery<UserEntity> query = session.createQuery("from UserEntity", UserEntity.class)
                    .setCacheable(true);
            List<UserEntity> resultList = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return resultList.stream().map(UserConverter::fromEntity).collect(Collectors.toList());
        }
    }

    @Override
    public long save(User user) {
        UserEntity userEntity = UserConverter.toEntity(user);
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(userEntity);
            long id = userEntity.getId();
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    @Override
    public User getUserById(Long id) {
        try (Session session = SFUtil.getSession()) {
            session.beginTransaction();
            UserEntity getUser = session.get(UserEntity.class, id);
            session.getTransaction().commit();
            return UserConverter.fromEntity(getUser);
        }
    }
}
