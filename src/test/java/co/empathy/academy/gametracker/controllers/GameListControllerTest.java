package co.empathy.academy.gametracker.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import co.empathy.academy.gametracker.models.GameList;
import co.empathy.academy.gametracker.models.GameWithPlaytime;
import co.empathy.academy.gametracker.services.GameListService;
import co.empathy.academy.gametracker.services.TokenSecurityService;
import co.empathy.academy.gametracker.utils.JWTUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GameListControllerTest {

    @Mock
    private GameListService gameListService;

    @Mock
    private TokenSecurityService tokenSecurityService;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private GameListController gameListController;

    
    @Test
    public void testCreateGameListSuccess() {
        // Mocking the required objects and data for the test
        String authorizationHeader = "Bearer <valid-jwt-token>";
        GameList gameListToCreate = new GameList();

        when(tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader))
                .thenReturn("<valid-jwt-token>");

        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        when(tokenSecurityService.loadUserByUsername(nullable(String.class))).thenReturn(userDetails);

        GameList createdGameList = new GameList();
        createdGameList.setId("123");
        when(gameListService.createGameList(gameListToCreate)).thenReturn(createdGameList);

         // Mock the behavior of jwtUtils to always return true for validateToken
         when(jwtUtils.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);

         // Perform the API call
         ResponseEntity<GameList> actualResponse = gameListController.createGameList(gameListToCreate, authorizationHeader);
 
         // Verify the response and status code
         assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
         assertEquals(createdGameList, actualResponse.getBody());
    }
    @Test
    public void testCreateGameListUnauthorized() {
        // Mocking the required objects and data for the test
        String authorizationHeader = "Bearer <invalid-jwt-token>";
        GameList gameListToCreate = new GameList();

        when(tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader))
                .thenReturn("<invalid-jwt-token>");

        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        when(tokenSecurityService.loadUserByUsername(nullable(String.class))).thenReturn(userDetails);

        // Perform the API call
        ResponseEntity<GameList> response = gameListController.createGameList(gameListToCreate, authorizationHeader);

        // Verify the response and status code
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

     @Test
    public void testUpdateGameListSuccess() {
        String listId = "123";
        String authorizationHeader = "Bearer <valid-jwt-token>";
        GameList updatedGameList = new GameList();
        updatedGameList.setId("123");

        when(tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader))
                .thenReturn("<valid-jwt-token>");

        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        when(tokenSecurityService.loadUserByUsername(nullable(String.class))).thenReturn(userDetails);

        when(gameListService.updateGameList(listId, updatedGameList)).thenReturn(updatedGameList);

        // Mock the behavior of jwtUtils to always return true for validateToken
        when(jwtUtils.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);

        // Perform the API call
        ResponseEntity<GameList> response = gameListController.updateGameList(listId, updatedGameList, authorizationHeader);

        // Verify the response and status code
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedGameList, response.getBody());
    }

     @Test
    public void testUpdateGameListNotFound() {
        String listId = "non-existent-id";
        String authorizationHeader = "Bearer <valid-jwt-token>";
        GameList updatedGameList = new GameList();
        updatedGameList.setId("non-existent-id");

        when(tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader))
                .thenReturn("<valid-jwt-token>");

        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        when(tokenSecurityService.loadUserByUsername(nullable(String.class))).thenReturn(userDetails);

        when(gameListService.updateGameList(listId, updatedGameList)).thenReturn(null);
        
        // Mock the behavior of jwtUtils to always return true for validateToken
        when(jwtUtils.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);
       
        // Perform the API call
        ResponseEntity<GameList> response = gameListController.updateGameList(listId, updatedGameList, authorizationHeader);

        // Verify the response and status code
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateGameFromGameListSuccess() {
        String listId = "existing-list-id";
        String gameId = "existing-game-id";
        String authorizationHeader = "Bearer <valid-jwt-token>";
        GameList currentGameList = new GameList();
        currentGameList.setId(listId);
        GameList newGameList = new GameList();
        newGameList.setId("new-list-id");

        GameWithPlaytime gameToUpdate = new GameWithPlaytime();
        gameToUpdate.setId(gameId);

        List<GameWithPlaytime> currentGames = new ArrayList<>();
        currentGames.add(gameToUpdate);
        currentGameList.setGames(currentGames);

        GameWithPlaytime updatedGame = new GameWithPlaytime();
        updatedGame.setId(gameId);

        List<GameWithPlaytime> newGames = new ArrayList<>();
        newGames.add(updatedGame);
        newGameList.setGames(newGames);

        when(tokenSecurityService.extractTokenFromAuthorizationHeader(authorizationHeader))
                .thenReturn("<valid-jwt-token>");

        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        when(tokenSecurityService.loadUserByUsername(nullable(String.class))).thenReturn(userDetails);

        when(gameListService.getGameList(listId)).thenReturn(currentGameList);
        when(gameListService.getGameList(newGameList.getId())).thenReturn(newGameList);
        when(gameListService.updateGameList(listId, currentGameList)).thenReturn(currentGameList);
        when(gameListService.updateGameList(newGameList.getId(), newGameList)).thenReturn(newGameList);

        // Mock the behavior of jwtUtils to always return true for validateToken
        when(jwtUtils.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);
        
        // Perform the API call
        ResponseEntity<GameList> response = gameListController.updateGameFromGameList(listId, gameId, newGameList, authorizationHeader);

        // Verify the response and status code
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currentGameList, response.getBody());
    }
    

}
