package co.empathy.academy.gametracker.controllers;

import co.empathy.academy.gametracker.models.GameWithPlaytime;
import co.empathy.academy.gametracker.services.GameWithPlayTimeService;
import co.empathy.academy.gametracker.services.TokenSecurityService;
import co.empathy.academy.gametracker.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameWithPlaytimeControllerTest {

    @Mock
    private GameWithPlayTimeService gameWithPlayTimeService;

    @Mock
    private TokenSecurityService tokenSecurityService;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private GameWithPlaytimeController gameWithPlaytimeController;

    /**
     * TEST: Update the playtime hours for a game with the specified ID
     */
    @Test
    public void updatePlaytimeHours() {
        String gameId = "123";
        String authorizationHeader = "Bearer <valid-jwt-token>";
        GameWithPlaytime updatedGameWithPlaytime = new GameWithPlaytime();
        updatedGameWithPlaytime.setId(gameId);

        when(tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader))
                .thenReturn("<valid-jwt-token>");

        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        when(tokenSecurityService.loadUserByUsername(nullable(String.class))).thenReturn(userDetails);

        when(gameWithPlayTimeService.updatePlaytimeHours(gameId, updatedGameWithPlaytime)).thenReturn(updatedGameWithPlaytime);

        when(jwtUtils.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);

        ResponseEntity<GameWithPlaytime> response = gameWithPlaytimeController.updatePlaytimeHours(gameId, updatedGameWithPlaytime, authorizationHeader);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedGameWithPlaytime, response.getBody());
    }

    /**
     * TEST: Update the playtime hours for a game with the specified ID NOT FOUND
     */
    @Test
    public void updatePlaytimeHoursNotFound() {
        String gameId = "non-existent-id";
        String authorizationHeader = "Bearer <valid-jwt-token>";
        GameWithPlaytime updatedGameWithPlaytime = new GameWithPlaytime();
        updatedGameWithPlaytime.setId(gameId);

        when(tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader))
                .thenReturn("<valid-jwt-token>");

        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        when(tokenSecurityService.loadUserByUsername(nullable(String.class))).thenReturn(userDetails);

        when(gameWithPlayTimeService.updatePlaytimeHours(gameId, updatedGameWithPlaytime)).thenReturn(null);

        when(jwtUtils.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);

        ResponseEntity<GameWithPlaytime> response = gameWithPlaytimeController.updatePlaytimeHours(gameId, updatedGameWithPlaytime, authorizationHeader);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * TEST: Get the game with playtime hours for a specific gameId
     */
    @Test
    public void getGameById() {
        String gameId = "64ae6cf260d53e4eff05ae9b";
        String authorizationHeader = "Bearer <valid-jwt-token>";
        GameWithPlaytime gameWithPlaytime = new GameWithPlaytime();
        gameWithPlaytime.setId(gameId);

        when(tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader))
                .thenReturn("<valid-jwt-token>");

        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        when(tokenSecurityService.loadUserByUsername(nullable(String.class))).thenReturn(userDetails);

        when(gameWithPlayTimeService.getGameById(gameId)).thenReturn(gameWithPlaytime);

        when(jwtUtils.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);

        ResponseEntity<GameWithPlaytime> response = gameWithPlaytimeController.getGameById(gameId, authorizationHeader);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gameWithPlaytime, response.getBody());
    }

    /**
     * TEST: Delete the game with playtime hours for a specific gameId
     */
    @Test
    public void deleteGameById() {
        String gameId = "64ae6cf260d53e4eff05ae9b";
        String authorizationHeader = "Bearer <valid-jwt-token>";
        GameWithPlaytime gameWithPlaytime = new GameWithPlaytime();
        gameWithPlaytime.setId(gameId);

        when(tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader))
                .thenReturn("<valid-jwt-token>");

        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        when(tokenSecurityService.loadUserByUsername(nullable(String.class))).thenReturn(userDetails);

        when(gameWithPlayTimeService.deleteGameById(gameId)).thenReturn(gameWithPlaytime);

        when(jwtUtils.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);

        ResponseEntity<GameWithPlaytime> response = gameWithPlaytimeController.deleteGameById(gameId, authorizationHeader);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gameWithPlaytime, response.getBody());
    }

    /**
     * TEST: Delete the game with playtime hours for a specific gameId NOT FOUND
     */
    @Test
    public void deleteGameByIdNotFound() {
        String gameId = "non-existent-id";
        String authorizationHeader = "Bearer <valid-jwt-token>";
        GameWithPlaytime updatedGameWithPlaytime = new GameWithPlaytime();
        updatedGameWithPlaytime.setId(gameId);

        when(tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader))
                .thenReturn("<valid-jwt-token>");

        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        when(tokenSecurityService.loadUserByUsername(nullable(String.class))).thenReturn(userDetails);

        when(gameWithPlayTimeService.deleteGameById(gameId)).thenReturn(null);

        when(jwtUtils.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);

         ResponseEntity<GameWithPlaytime> response = gameWithPlaytimeController.deleteGameById(gameId, authorizationHeader);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

}