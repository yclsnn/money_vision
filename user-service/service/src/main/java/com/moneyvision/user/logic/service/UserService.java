package com.moneyvision.user.logic.service;

import com.moneyvision.user.model.UserModel;
import com.moneyvision.user.model.UserQueryModel;

import java.util.List;

public interface UserService {
    UserModel createUser(UserModel request);
    UserModel updateUser(Long id, UserModel request);
    void deleteUser(Long id);
    UserModel getUserById(Long id);
    UserModel getUserByUsername(String username);
    UserModel getUserByEmail(String email);
    List<UserModel> getAllUsers();
    List<UserModel> searchUsers(UserQueryModel query);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 