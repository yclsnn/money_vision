package com.moneyvision.user.service;

import com.moneyvision.user.model.CreateUserModel;
import com.moneyvision.user.model.UpdateUserModel;
import com.moneyvision.user.model.UserModel;

import java.util.List;

public interface UserRestService {
    UserModel createUser(CreateUserModel request);
    UserModel updateUser(Long id, UpdateUserModel request);
    void deleteUser(Long id);
    UserModel getUserById(Long id);
    UserModel getUserByUsername(String username);
    UserModel getUserByEmail(String email);
    List<UserModel> getAllUsers();
} 