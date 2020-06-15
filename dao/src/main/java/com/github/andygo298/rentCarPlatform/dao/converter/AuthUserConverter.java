package com.github.andygo298.rentCarPlatform.dao.converter;

import com.github.andygo298.rentCarPlatform.dao.entity.AuthUserEntity;
import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.User;

public class AuthUserConverter {

    public static AuthUser fromEntity(AuthUserEntity authUser) {
        if (authUser == null) {
            return null;
        }
        return new AuthUser(
                authUser.getId(),
                authUser.getLogin(),
                authUser.getPassword(),
                authUser.getRole(),
                authUser.getUserId()
        );
    }

    public static AuthUserEntity toEntity(AuthUser authUser) {
        if (authUser == null) {
            return null;
        }
        final AuthUserEntity authUserEntity = new AuthUserEntity();
        authUserEntity.setId(authUser.getId());
        authUserEntity.setLogin(authUser.getLogin());
        authUserEntity.setPassword(authUser.getPassword());
        authUserEntity.setRole(authUser.getRole());
        authUserEntity.setUserId(authUser.getUserId());
        return authUserEntity;
    }
}
