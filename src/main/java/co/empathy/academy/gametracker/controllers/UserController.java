package co.empathy.academy.gametracker.controllers;

import co.empathy.academy.gametracker.models.dtos.AuthDTO;
import co.empathy.academy.gametracker.models.dtos.ChangePasswordDTO;
import co.empathy.academy.gametracker.models.User;
import co.empathy.academy.gametracker.models.dtos.UserUpdateDTO;
import co.empathy.academy.gametracker.services.TokenSecurityService;
import co.empathy.academy.gametracker.services.UserService;
import co.empathy.academy.gametracker.utils.JWTUtils;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class UserController {

    private final UserService userService;
    private final TokenSecurityService tokenSecurityService;
    private final JWTUtils jwtUtils;

    public UserController(UserService userService, TokenSecurityService tokenSecurityService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.tokenSecurityService = tokenSecurityService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Register a new user.
     *
     * @param user The user to register.
     * @return ResponseEntity containing the registered user if successful, or an
     * error response if the registration failed.
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Extract the necessary details (username, email, password) from the request body
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();

        try {
            // Call the registerUser method of the userService to register the user
            User registeredUser = userService.registerUser(username, email, password);

            // Return the registered user with HTTP status 201 (Created)
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (Exception e) {
            // Return an error response with HTTP status 400 (Bad Request) and the error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Login a user.
     *
     * @param user The user to login.
     * @return ResponseEntity containing the JWT token if successful, or an error
     * response if the login failed.
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        // Extract the necessary details (username, password) from the request body
        String username = user.getUsername();
        String password = user.getPassword();

        try {
            // Call the loginUser method of the userService to authenticate the user and generate JWT token
            String token = userService.loginUser(username, password);
            String id = userService.getUserId(username);
            String email = userService.getUserEmail(username);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }

            AuthDTO authDTO = new AuthDTO(username, email, token, id);
            // Return the JWT token with HTTP status 200 (OK)
            return ResponseEntity.ok(authDTO);
        } catch (Exception e) {
            // Return an error response with HTTP status 401 (Unauthorized) and the error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /**
     * Update user's email and username.
     *
     * @param userUpdateDTO The updated user data.
     * @param authorizationHeader the authorization header.
     * @return ResponseEntity containing the updated user if successful, or an
     * error response if the update failed.
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @RequestHeader("Authorization") String authorizationHeader) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = tokenSecurityService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

        // Check if the token is valid
        if (token == null || !jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Extract the necessary details (username, email) from the request body
        String username = userUpdateDTO.getUsername();
        String email = userUpdateDTO.getEmail();

        String currentUsername = userUpdateDTO.getCurrentUsername();

        try {
            // Call the updateUser method of the userService to update the user's email and username
            userService.updateUser(currentUsername, username, email);

            // Return the updated user with HTTP status 200 (OK)
            return ResponseEntity.ok(userUpdateDTO);
        } catch (Exception e) {
            // Return an error response with HTTP status 400 (Bad Request) and the error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Change user's password.
     *
     * @param changePasswordDTO The updated user data.
     * @param authorizationHeader the authorization header.
     * @return ResponseEntity containing the updated user if successful, or an
     * error response if the update failed.
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, @RequestHeader("Authorization") String authorizationHeader) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = tokenSecurityService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

        // Check if the token is valid
        if (token == null || !jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            userService.changePassword(changePasswordDTO.getCurrentUsername(), changePasswordDTO.getNewPassword());

            return ResponseEntity.ok("Password changed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
