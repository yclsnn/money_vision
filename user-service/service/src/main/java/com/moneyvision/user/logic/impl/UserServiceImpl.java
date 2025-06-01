package com.moneyvision.user.logic.impl;

import com.moneyvision.user.entity.UserEntity;
import com.moneyvision.user.mapper.UserMapper;
import com.moneyvision.user.model.UserModel;
import com.moneyvision.user.model.UserQueryModel;
import com.moneyvision.user.repository.UserRepository;
import com.moneyvision.user.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserModel createUser(UserModel request) {
        if (existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        UserEntity entity = userMapper.toEntity(request);
        entity = userRepository.save(entity);
        return userMapper.toModel(entity);
    }

    @Override
    @Transactional
    public UserModel updateUser(Long id, UserModel request) {
        UserEntity existingUser = getUserEntityById(id);
        
        if (!existingUser.getUsername().equals(request.getUsername()) && existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (!existingUser.getEmail().equals(request.getEmail()) && existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        userMapper.updateEntity(existingUser, request);
        existingUser = userRepository.save(existingUser);
        return userMapper.toModel(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = getUserEntityById(id);
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public UserModel getUserById(Long id) {
        return userMapper.toModel(getUserEntityById(id));
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return userMapper.toModel(getUserEntityByUsername(username));
    }

    @Override
    public UserModel getUserByEmail(String email) {
        return userMapper.toModel(getUserEntityByEmail(email));
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserModel> searchUsers(UserQueryModel query) {
        Specification<UserEntity> spec = Specification.where(null);

        if (StringUtils.hasText(query.getUsername())) {
            spec = spec.and((root, q, cb) -> 
                cb.like(cb.lower(root.get("username")), "%" + query.getUsername().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(query.getEmail())) {
            spec = spec.and((root, q, cb) -> 
                cb.like(cb.lower(root.get("email")), "%" + query.getEmail().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(query.getFirstName())) {
            spec = spec.and((root, q, cb) -> 
                cb.like(cb.lower(root.get("firstName")), "%" + query.getFirstName().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(query.getLastName())) {
            spec = spec.and((root, q, cb) -> 
                cb.like(cb.lower(root.get("lastName")), "%" + query.getLastName().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(query.getPhoneNumber())) {
            spec = spec.and((root, q, cb) -> 
                cb.like(root.get("phoneNumber"), "%" + query.getPhoneNumber() + "%"));
        }

        if (query.getActive() != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("active"), query.getActive()));
        }

        return userRepository.findAll(spec).stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    private UserEntity getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    private UserEntity getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
} 