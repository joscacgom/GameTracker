package co.empathy.academy.gametracker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.empathy.academy.gametracker.models.GameWithPlaytime;
import co.empathy.academy.gametracker.services.GameWithPlayTimeService;
import co.empathy.academy.gametracker.services.UserService;
import co.empathy.academy.gametracker.utils.JWTUtils;

@RestController
@RequestMapping("/api/game-with-playtime")
public class GameWithPlaytimeController {

    private final GameWithPlayTimeService gameWithPlayTimeService;
    private final JWTUtils jwtUtils;
    private final UserService userService;

    public GameWithPlaytimeController(GameWithPlayTimeService gameWithPlayTimeService, JWTUtils jwtUtils, UserService userService) {
        this.gameWithPlayTimeService = gameWithPlayTimeService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    /**
     * Update the playtime hours for a game with the specified ID.
     *
     * @param gameWithPlaytime    The updated game with playtime hours.
     * @param id                  The ID of the game.
     * @param authorizationHeader The Authorization header containing the JWT token.
     * @return ResponseEntity containing the updated game with playtime hours if successful,
     * or a not found response if the game was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GameWithPlaytime> updatePlaytimeHours(
            @RequestBody GameWithPlaytime gameWithPlaytime,
            @PathVariable("id") String id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

        // Check if the token is valid
        if (token == null || !jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Update the playtime hours for the game
        GameWithPlaytime updatedGameWithPlaytime = gameWithPlayTimeService.updatePlaytimeHours(id, gameWithPlaytime);

        // Check if the game was found and updated successfully
        if (updatedGameWithPlaytime == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Return the updated game with playtime hours
        return ResponseEntity.ok(updatedGameWithPlaytime);
    }

    /*
     * Helper method to extract the JWT token from the Authorization header.
     *
     * Parameters:
     *   - authorizationHeader: The Authorization header value
     *
     * Returns:
     *   - The JWT token, or null if not found
     */
    private String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}

