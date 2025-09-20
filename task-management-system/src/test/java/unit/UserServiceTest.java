package unit;

import com.testing.taskmanager.entity.User;
import com.testing.taskmanager.repository.UserRepository;
import com.testing.taskmanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TDD-style unit tests for the UserService.
 *
 * @author Gemini
 * @version 1.0
 */
@DisplayName("UserService Unit Tests (TDD)")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * TDD Cycle: Test user authentication with valid credentials.
     *
     * Red: Write a test that expects `authenticateUser` to return a `User` for valid credentials.
     * Green: Implement `authenticateUser` to fetch the user and compare passwords.
     * Refactor: N/A, the implementation is straightforward.
     */
    @Test
    @DisplayName("should authenticate a user with correct credentials")
    public void testAuthenticateUser_CorrectCredentials_Success() {
        // Red
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("password123");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        // Green
        Optional<User> authenticatedUser = userService.authenticateUser("testuser", "password123");

        assertTrue(authenticatedUser.isPresent());
        assertEquals("testuser", authenticatedUser.get().getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    /**
     * TDD Cycle: Test user authentication with incorrect password.
     *
     * Red: Write a test expecting an empty `Optional` for an incorrect password.
     * Green: The existing code for the previous test already handles this.
     * Refactor: N/A.
     */
    @Test
    @DisplayName("should not authenticate a user with incorrect password")
    public void testAuthenticateUser_IncorrectPassword_Failure() {
        // Red
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("password123");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        // Green
        Optional<User> authenticatedUser = userService.authenticateUser("testuser", "wrongpassword");

        assertFalse(authenticatedUser.isPresent());
        verify(userRepository, times(1)).findByUsername("testuser");
    }
}
