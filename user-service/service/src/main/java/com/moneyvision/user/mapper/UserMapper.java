package com.moneyvision.user.mapper;

import com.moneyvision.user.entity.UserEntity;
import com.moneyvision.user.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserModel toModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setPhoneNumber(entity.getPhoneNumber());
        model.setActive(entity.isActive());
        return model;
    }

    public UserEntity toEntity(UserModel model) {
        if (model == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        updateEntity(entity, model);
        return entity;
    }

    public void updateEntity(UserEntity entity, UserModel model) {
        if (model == null) {
            return;
        }

        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        if (model.getPassword() != null) {
            // TODO: Add password encryption
            entity.setPassword(model.getPassword());
        }
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setPhoneNumber(model.getPhoneNumber());
        entity.setActive(model.isActive());
    }
} 