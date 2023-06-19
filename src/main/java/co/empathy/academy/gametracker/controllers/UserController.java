package co.empathy.academy.gametracker.controllers;

import co.empathy.academy.gametracker.models.User;
import co.empathy.academy.gametracker.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * Register a new user.
     * 
     * @param user The user to register.
     * 
     * @return ResponseEntity containing the registered user if successful, or an
     * error response if the registration failed.
     *
     */
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

    /*
     * Login a user.
     * 
     * @param user The user to login.
     * 
     * @return ResponseEntity containing the JWT token if successful, or an error
     * response if the login failed.
     *
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        // Extract the necessary details (username, password) from the request body
        String username = user.getUsername();
        String password = user.getPassword();

        try {
            // Call the loginUser method of the userService to authenticate the user and generate JWT token
            String token = userService.loginUser(username, password);
            if(token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
            // Return the JWT token with HTTP status 200 (OK)
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            // Return an error response with HTTP status 401 (Unauthorized) and the error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
