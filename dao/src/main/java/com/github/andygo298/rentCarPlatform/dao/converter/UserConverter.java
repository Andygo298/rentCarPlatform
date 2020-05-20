package com.github.andygo298.rentCarPlatform.dao.converter;

import com.github.andygo298.rentCarPlatform.dao.entity.UserEntity;
import com.github.andygo298.rentCarPlatform.model.User;

import java.util.stream.Collectors;

public class UserConverter {
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

        userEntity.setAuthUserEntity(AuthUserConverter.toEntity(user.getAuthUser()));
        userEntity.setPaymentEntities(user.getPayments().stream().map(PaymentConverter::toEntity).collect(Collectors.toSet()));
        userEntity.setOrderEntities(user.getOrders().stream().map(OrderConverter::toEntity).collect(Collectors.toSet()));
        return userEntity;
    }

    public static User fromEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.isBlocked(),
                AuthUserConverter.fromEntity(userEntity.getAuthUserEntity()),
                userEntity.getPaymentEntities().stream().map(PaymentConverter::fromEntity).collect(Collectors.toSet()),
                userEntity.getOrderEntities().stream().map(OrderConverter::fromEntity).collect(Collectors.toSet())
        );
    }
}
