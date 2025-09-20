package com.testing.taskmanager.service;

import com.testing.taskmanager.entity.User;
import com.testing.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling User-related business logic.
 *
 * @author Gemini
 * @version 1.0
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user based on their username and password.
     *
     * @param username The username to authenticate.
     * @param password The password to authenticate.
     * @return An Optional containing the authenticated User if successful, otherwise empty.
     */
    public Optional<User> authenticateUser(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }

    /**
     * Creates a new user.
     *
     * @param user The user object to be created.
     * @return The created User object.
     */
    public User createUser(User user) {
        // TODO: Implement password hashing in a real application
        return userRepository.save(user);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username to find.
     * @return An Optional containing the User if found, otherwise empty.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Fixes SQL Injection by using a secure findByUsername method.
     *
     * Before Fix (Vulnerable to SQL Injection):
     * ```
     * public User findUserVulnerable(String username) {
     * // This is a naive example and is not how JPA works, but demonstrates the concept
     * // String sql = "SELECT * FROM user WHERE username = '" + username + "'";
     * // return jdbcTemplate.queryForObject(sql, new UserRowMapper());
     * return userRepository.findByUsername(username).orElse(null);
     * }
     * ```
     * After Fix (Secure with parameterized query):
     * The `findByUsername` method in the JPA repository automatically handles this by using
     * parameterized queries, preventing SQL injection.
     *
     * @param username The username to find.
     * @return An Optional containing the User if found.
     */
    public Optional<User> findUserSecure(String username) {
        return userRepository.findByUsername(username);
    }
}
