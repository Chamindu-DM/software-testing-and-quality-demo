package com.testing.taskmanager.controller;

import com.testing.taskmanager.entity.User;
import com.testing.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * REST controller for User-related operations.
 *
 * @author Gemini
 * @version 1.0
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for user login.
     *
     * @param user The user object containing username and password.
     * @return A ResponseEntity with the user object if authenticated, or an error.
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        try {
            Optional<User> authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
            if (authenticatedUser.isPresent()) {
                LOGGER.info("User logged in: " + user.getUsername());
                return ResponseEntity.ok(authenticatedUser.get());
            } else {
                LOGGER.warning("Failed login attempt for user: " + user.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            LOGGER.severe("Login error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to register a new user.
     *
     * @param user The user object to create.
     * @return A ResponseEntity with the created user object.
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            LOGGER.severe("User registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
