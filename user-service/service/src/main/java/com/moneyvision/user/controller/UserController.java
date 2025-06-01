package com.moneyvision.user.controller;

import com.moneyvision.user.model.CreateUserModel;
import com.moneyvision.user.model.UpdateUserModel;
import com.moneyvision.user.model.UserModel;
import com.moneyvision.user.logic.service.UserService;
import com.moneyvision.user.service.UserRestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Operations about users")
public class UserController implements UserRestService {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user with the provided information"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping
    public UserModel createUser(
            @Parameter(description = "User creation details", required = true)
            @Valid @RequestBody CreateUserModel request) {
        return userService.createUser(request);
    }

    @Override
    @Operation(
        summary = "Update an existing user",
        description = "Updates the user information for the specified user ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "409", description = "Username or email already in use")
    })
    @PutMapping("/{id}")
    public UserModel updateUser(
            @Parameter(description = "ID of the user to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "User update details", required = true)
            @Valid @RequestBody UpdateUserModel request) {
        return userService.updateUser(id, request);
    }

    @Override
    @Operation(
        summary = "Delete a user",
        description = "Soft deletes the user with the specified ID by setting active status to false"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public void deleteUser(
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Override
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves user information by their ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public UserModel getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true)
            @PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Override
    @Operation(
        summary = "Get user by username",
        description = "Retrieves user information by their username"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/username/{username}")
    public UserModel getUserByUsername(
            @Parameter(description = "Username of the user to retrieve", required = true)
            @PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @Override
    @Operation(
        summary = "Get user by email",
        description = "Retrieves user information by their email address"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email/{email}")
    public UserModel getUserByEmail(
            @Parameter(description = "Email of the user to retrieve", required = true)
            @PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @Override
    @Operation(
        summary = "Get all users",
        description = "Retrieves a list of all users in the system"
    )
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }
} 