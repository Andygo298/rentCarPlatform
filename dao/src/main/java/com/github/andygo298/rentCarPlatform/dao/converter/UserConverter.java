package com.github.andygo298.rentCarPlatform.dao.converter;

import com.github.andygo298.rentCarPlatform.dao.entity.UserEntity;
import com.github.andygo298.rentCarPlatform.model.User;

public class UserConverter {

    public static User fromEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.isBlocked()
        );
    }

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setBlocked(user.isBlocked());
        return userEntity;
    }
}
