package co.empathy.academy.gametracker.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.empathy.academy.gametracker.models.GameWithPlaytime;
import co.empathy.academy.gametracker.services.GameWithPlayTimeService;
import co.empathy.academy.gametracker.services.TokenSecurityService;
import co.empathy.academy.gametracker.utils.JWTUtils;

@RestController
@RequestMapping("games/game-with-playtime")
public class GameWithPlaytimeController {

    private final GameWithPlayTimeService gameWithPlayTimeService;
    private final JWTUtils jwtUtils;
    private final TokenSecurityService tokenSecurityService;

    public GameWithPlaytimeController(GameWithPlayTimeService gameWithPlayTimeService, TokenSecurityService tokenSecurityService ,JWTUtils jwtUtils) {
        this.gameWithPlayTimeService = gameWithPlayTimeService;
        this.jwtUtils = jwtUtils;
        this.tokenSecurityService = tokenSecurityService;
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
    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/{id}")
    public ResponseEntity<GameWithPlaytime> updatePlaytimeHours(
            @PathVariable("id") String id,
            @RequestBody GameWithPlaytime gameWithPlaytime,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = tokenSecurityService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

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

    /**
     * Get the game with playtime hours for a specific gameId.
     * @param id The ID of the game.
     * @param authorizationHeader The Authorization header containing the JWT token.
     * @return ResponseEntity containing the game with playtime hours if successful,
     * or a not found response if the game was not found.
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/{id}")
    public ResponseEntity<GameWithPlaytime> getGameById(
            @PathVariable("id") String id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = tokenSecurityService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

        // Check if the token is valid
        if (token == null || !jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Get the game with playtime hours
        GameWithPlaytime gameWithPlaytime = gameWithPlayTimeService.getGameById(id);

        // Check if the game was not found
        if (gameWithPlaytime == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Return the game with playtime hours
        return ResponseEntity.ok(gameWithPlaytime);
    }


    /**
     * Get the games with playtime hours for a specific user.
     *
     * @param userId              The ID of the user.
     * @param authorizationHeader The Authorization header containing the JWT token.
     * @return ResponseEntity containing the list of games with playtime hours if successful,
     * or a not found response if the user was not found or the user's games were not found.
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GameWithPlaytime>> getGamesByUserId(
            @PathVariable("userId") String userId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = tokenSecurityService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

        // Check if the token is valid
        if (token == null || !jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Get the games with playtime hours for the user
        List<GameWithPlaytime> games = gameWithPlayTimeService.getGamesByUserId(userId);

        // Check if the user or their games were not found
        if (games == null || games.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Return the list of games with playtime hours
        return ResponseEntity.ok(games);
    }
    /**
     * Delete the game with playtime hours for a specific gameId.
     * @param id The ID of the game.
     * @param authorizationHeader The Authorization header containing the JWT token.
     * @return ResponseEntity containing the deleted game with playtime hours if successful,
     * or a not found response if the game was not found.
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping("/{id}")
    public ResponseEntity<GameWithPlaytime> deleteGameById(
            @PathVariable("id") String id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = tokenSecurityService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

        // Check if the token is valid
        if (token == null || !jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Delete the game with playtime hours
        GameWithPlaytime deletedGameWithPlaytime = gameWithPlayTimeService.deleteGameById(id);

        // Check if the game was not found
        if (deletedGameWithPlaytime == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Return the deleted game with playtime hours
        return ResponseEntity.ok(deletedGameWithPlaytime);
    }

}

