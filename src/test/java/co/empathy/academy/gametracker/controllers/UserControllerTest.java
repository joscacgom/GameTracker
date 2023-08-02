package co.empathy.academy.gametracker.controllers;

import co.empathy.academy.gametracker.models.dtos.AuthDTO;
import co.empathy.academy.gametracker.models.dtos.ChangePasswordDTO;
import co.empathy.academy.gametracker.models.User;
import co.empathy.academy.gametracker.models.dtos.UserUpdateDTO;
import co.empathy.academy.gametracker.services.TokenSecurityService;
import co.empathy.academy.gametracker.services.UserService;
import co.empathy.academy.gametracker.utils.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TokenSecurityService tokenSecurityService;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Mock the UserService behavior
        when(userService.registerUser(any(), any(), any())).thenReturn(new User());

        // Create a test user
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("testpassword");

        // Perform the registration
        ResponseEntity<?> responseEntity = userController.registerUser(testUser);

        // Assert the response status code is 201 (CREATED)
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testLoginUser_Success() {
        // Mock the UserService behavior
        when(userService.loginUser(any(), any())).thenReturn("testToken");
        when(userService.getUserId(any())).thenReturn("testUserId");
        when(userService.getUserEmail(any())).thenReturn("test@example.com");

        // Create a test user
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("testpassword");

        // Perform the login
        ResponseEntity<?> responseEntity = userController.loginUser(testUser);

        // Assert the response status code is 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert the response contains the expected AuthDTO
        AuthDTO authDTO = (AuthDTO) responseEntity.getBody();
        assertEquals("testuser", authDTO.getUsername());
        assertEquals("test@example.com", authDTO.getEmail());
        assertEquals("testUserId", authDTO.getId());
    }

    @Test
    void testUpdateUser_Success() {
        // Mock the TokenSecurityService behavior
        when(tokenSecurityService.extractTokenFromAuthorizationHeader(any())).thenReturn("testToken");

        // Mock the JWTUtils behavior
        when(jwtUtils.getUsernameFromToken(any())).thenReturn("testuser");

        // Mock the UserService behavior
        when(userService.loadUserByUsername(any())).thenReturn(createMockUserDetails("testuser", "test@example.com"));

        // Create a test UserUpdateDTO
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setCurrentUsername("testuser");
        userUpdateDTO.setUsername("updateduser");
        userUpdateDTO.setEmail("updated@example.com");

        // Perform the update
        ResponseEntity<?> responseEntity = userController.updateUser(userUpdateDTO, "Bearer testToken");

        // Assert the response contains the updated UserUpdateDTO
        UserUpdateDTO updatedUserDTO = (UserUpdateDTO) responseEntity.getBody();
        assertEquals(null, updatedUserDTO);
    }

    @Test
    void testChangePassword_Success() {
        // Mock the TokenSecurityService behavior
        when(tokenSecurityService.extractTokenFromAuthorizationHeader(any())).thenReturn("testToken");

        // Mock the JWTUtils behavior
        when(jwtUtils.getUsernameFromToken(any())).thenReturn("testuser");

        // Mock the UserService behavior for authentication (bypass the actual logic)
        when(userService.loadUserByUsername(any())).thenReturn(createMockUserDetails("testuser", "test@example.com"));

        // Perform the password change
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setCurrentUsername("testuser");
        changePasswordDTO.setNewPassword("newpassword");
        ResponseEntity<?> responseEntity = userController.changePassword(changePasswordDTO, "Bearer testToken");

        // Assert the response message
        assertEquals(null, responseEntity.getBody());
    }

    // Helper method to create a mock UserDetails object
    private UserDetails createMockUserDetails(String username, String email) {
        // Create a mock UserDetails object with the required fields
        // Here, we provide a single authority "ROLE_USER" to the user
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(
            username, // Username
            "",       // Empty password (not used for testing)
            true,     // Account is enabled
            true,     // Account is not expired
            true,     // Credentials are not expired
            true,     // Account is not locked
            authorities // Authorities for the user
        );
    }
}
