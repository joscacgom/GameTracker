package co.empathy.academy.gametracker.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import co.empathy.academy.gametracker.utils.JWTUtils;

@Service
public class TokenSecurityService {

    private final UserService userService;
    private final JWTUtils jwtUtils;

    public TokenSecurityService(UserService userService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Validate the JWT token.
     * 
     * @param token       The JWT token
     * @param userDetails The user details to validate against
     * @return True if the token is valid, false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        return jwtUtils.validateToken(token, userDetails);
    }

    /**
     * Load the user details from the database.
     * 
     * @param username The username to load
     * @return The user details from the database or null if not found
     */
    public UserDetails loadUserByUsername(String username) {
        return userService.loadUserByUsername(username);
    }

    /**
     * Helper method to extract the JWT token from the Authorization header.
     *
     * @param authorizationHeader The Authorization header value
     * @return The JWT token, or null if not found
     */
    public String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
