package co.empathy.academy.gametracker.controllers;

import java.util.List;

import co.empathy.academy.gametracker.models.Game;
import co.empathy.academy.gametracker.services.GameService;
import co.empathy.academy.gametracker.services.GameWithPlayTimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.empathy.academy.gametracker.models.GameList;
import co.empathy.academy.gametracker.models.GameWithPlaytime;
import co.empathy.academy.gametracker.services.GameListService;
import co.empathy.academy.gametracker.services.UserService;
import co.empathy.academy.gametracker.utils.JWTUtils;

@RestController
@RequestMapping("games/lists")
public class GameListController {

    private final GameListService gameListService;
    private final GameService gameService;
    private final GameWithPlayTimeService gameWithPlayTimeService;
    private final UserService userService;
    private final JWTUtils jwtUtils;

    public GameListController(GameListService gameListService, GameService gameService, GameWithPlayTimeService gameWithPlayTimeService, UserService userService, JWTUtils jwtUtils) {
        this.gameListService = gameListService;
        this.gameService = gameService;
        this.gameWithPlayTimeService = gameWithPlayTimeService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Endpoint to create a new game list.
     *
     * @param gameList GameList object representing the new game list to be created
     * @param authorizationHeader Bearer <JWT Token>
     * @return 201 Created: If the game list was created successfully or
     * 401 Unauthorized: If the request is not authorized or the token is invalid
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping
    public ResponseEntity<GameList> createGameList(@RequestBody GameList gameList, @RequestHeader("Authorization") String authorizationHeader) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

        // Check if the token is valid
        if (token == null || !jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Create the game list
        GameList createdGameList = gameListService.createGameList(gameList);

        // Return the created game list
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGameList);
    }

    /**
     * Endpoint to update a game list.
     *
     * @param listId ID of the game list to be updated
     * @param gameList GameList object representing the updated game list
     * @param authorizationHeader Bearer <JWT Token>
     * @return 200 OK: If the game list was updated successfully or
     * 404 Not Found: If the specified game list does not exist or
     * 401 Unauthorized: If the request is not authorized or the token is invalid
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/{listId}")
    public ResponseEntity<GameList> updateGameList(@PathVariable("listId") String listId, @RequestBody GameList gameList, @RequestHeader("Authorization") String authorizationHeader) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));
        // Check if the token is valid
        if (token == null || !jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Update the game list
        GameList updatedGameList = gameListService.updateGameList(listId, gameList);
        // Check if the game list was found
        if (updatedGameList == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Return the updated game list
        return ResponseEntity.ok(updatedGameList);
    }

    /**
     * Endpoint to update a game within a game list.
     *
     * @param listId ID of the game list containing the game to be updated
     * @param gameId ID of the game to be updated
     * @param gameList GameList object representing the updated game list with the modified game
     * @param authorizationHeader Bearer <JWT Token>
     * @return 200 OK: If the game was updated successfully or
     * 404 Not Found: If the specified game list or game does not exist or
     * 401 Unauthorized: If the request is not authorized or the token is invalid
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/{listId}/game/{gameId}")
    public ResponseEntity<GameList> updateGameFromGameList(
            @PathVariable("listId") String listId,
            @PathVariable("gameId") String gameId,
            @RequestBody GameList gameList,
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

        // Get the current game list
        GameList currentGameList = gameListService.getGameList(listId);

        // Check if the current game list exists
        if (currentGameList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Get the new game list
        GameList newGameList = gameListService.getGameList(gameList.getId());

        // Check if the new game list exists
        if (newGameList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Find the game to be updated in the current game list
        List<GameWithPlaytime> currentGames = currentGameList.getGames();
        GameWithPlaytime gameToUpdate = null;

        for (GameWithPlaytime game : currentGames) {
            if (game.getId().equals(gameId)) {
                gameToUpdate = game;
                break;
            }
        }

        // Check if the game to be updated was found
        if (gameToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Remove the game to be updated from the current game list
        currentGames.remove(gameToUpdate);

        // Add the updated game to the new game list
        gameList.getGames().add(gameToUpdate);

        // Update the current game list
        GameList updatedCurrentGameList = gameListService.updateGameList(listId, currentGameList);

        // Check if the current game list was updated successfully
        if (updatedCurrentGameList == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Update the new game list
        GameList updatedNewGameList = gameListService.updateGameList(newGameList.getId(), gameList);

        // Check if the new game list was updated successfully
        if (updatedNewGameList == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Return the updated current game list
        return ResponseEntity.ok(updatedCurrentGameList);
    }

    /**
     * Endpoint to add a game to a game list.
     *
     * @param listId ID of the game list where the game is going to be added
     * @param gameId ID of the game to be added
     * @param gameList GameList object representing the updated game list with the added game
     * @param authorizationHeader Bearer <JWT Token>
     * @return 200 OK: If the game was added successfully or
     * 302 Found: If the game is already in the list or
     * 404 Not Found: If the specified game list or game does not exist or
     * 401 Unauthorized: If the request is not authorized or the token is invalid
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/{listId}/add/{gameId}")
    public ResponseEntity<GameList> addGameToGameList(
            @PathVariable("listId") String listId,
            @PathVariable("gameId") String gameId,
            @RequestBody GameList gameList,
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

        // Get the current game list
        GameList currentGameList = gameListService.getGameList(listId);

        // Check if the current game list exists
        if (currentGameList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Get the current game
        Game currentGame = gameService.getGame(Long.valueOf(gameId));

        // Check if the current game exists
        if (currentGame == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Check the game to be added is not in the current game list
        List<GameWithPlaytime> currentGames = currentGameList.getGames();
        if (currentGames != null) {
            for (GameWithPlaytime game : currentGames) {
                if (game.getGame().getId().equals(gameId)) {
                    return ResponseEntity.status(HttpStatus.FOUND).build();
                }
            }
        }

        // Create the game to add
        GameWithPlaytime gameToAdd = new GameWithPlaytime();
        gameToAdd.setGame(currentGame);
        gameToAdd.setUser(userService.getUser(jwtUtils.getUsernameFromToken(token)));
        gameToAdd.setGameList(currentGameList);
        gameToAdd.setPlaytimeHours(0);

        // Save it on ddbb
        GameWithPlaytime gameAdded = gameWithPlayTimeService.createGame(gameToAdd);;

        // Check if the game adding was successfull
        if (gameAdded == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Update the current game list
        GameList updatedCurrentGameList = gameListService.addGameToGameList(listId, gameAdded);


        // Check if the current game list was updated successfully
        if (updatedCurrentGameList == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Return the updated current game list
        return ResponseEntity.ok(updatedCurrentGameList);
    }

    /**
     * Endpoint to retrieve game lists by user ID.
     *
     * @param userId ID of the user whose game lists are to be retrieved
     * @param authorizationHeader Bearer <JWT Token>
     * @return 200 OK: If the game lists were retrieved successfully or
     * 404 Not Found: If no game lists were found for the specified user or
     * 401 Unauthorized: If the request is not authorized or the token is invalid
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GameList>> getGameListsByUserId(
            @PathVariable("userId") String userId,
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

        // Retrieve game lists by user ID
        List<GameList> gameLists = gameListService.getGameListsByUserId(userId);

        // Check if game lists were found
        if (gameLists == null || gameLists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Return the game lists
        return ResponseEntity.ok(gameLists);
    }

    /**
     * Endpoint to delete a game from a game list.
     *
     * @param listId ID of the game list containing the game to be deleted
     * @param gameId ID of the game to be deleted
     * @param authorizationHeader Bearer <JWT Token>
     * @return 200 OK: If the game was deleted successfully or
     * 404 Not Found: If the specified game list or game does not exist or
     * 401 Unauthorized: If the request is not authorized or the token is invalid
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping("/{listId}/games/{gameId}")
    public ResponseEntity<GameList> deleteGameFromGameList(
            @PathVariable("listId") String listId,
            @PathVariable("gameId") String gameId,
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

        // Get the game list
        GameList gameList = gameListService.getGameList(listId);


        // Check if the game list exists
        if (gameList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Find the game to be deleted in the game list
        List<GameWithPlaytime> games = gameList.getGames();
        GameWithPlaytime gameToDelete = null;

        // check if games is not null
        if (games == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        for (GameWithPlaytime game : games) {
            if (game.getId().toString().equals(gameId)) {
                gameToDelete = game;
                break;
            }
        }

        // Check if the game to be deleted was found
        if (gameToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Remove the game from the game list
        games.remove(gameToDelete);

        // Remove gamewithplaytime
        gameWithPlayTimeService.deleteGameById(gameId);


        // Update the game list
        GameList updatedGameList = gameListService.updateGameList(listId, gameList);

        // Check if the game list was updated successfully
        if (updatedGameList == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Return the updated game list
        return ResponseEntity.ok(updatedGameList);
    }

    /**
     * Endpoint to retrieve a game list by its ID.
     *
     * @param listId ID of the game list to be retrieved
     * @param authorizationHeader Bearer <JWT Token>
     * @return 200 OK: If the game list was retrieved successfully or
     * 404 Not Found: If the specified game list does not exist or
     * 401 Unauthorized: If the request is not authorized or the token is invalid
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/{listId}")
    public ResponseEntity<GameList> getGameList(
            @PathVariable("listId") String listId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

        // Check if the token is valid
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Get the game list by ID
        GameList gameList = gameListService.getGameList(listId);

        // Check if the game list exists
        if (gameList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Check if the token is valid
        if (!jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Return the game list
        return ResponseEntity.ok(gameList);
    }

    /**
     * Endpoint to delete a game list.
     *
     * @param listId ID of the game list to be deleted
     * @param authorizationHeader Bearer <JWT Token>
     * @return 204 No Content: If the game list was deleted successfully or
     * 404 Not Found: If the specified game list does not exist or
     * 401 Unauthorized: If the request is not authorized or the token is invalid
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteGameList(
            @PathVariable("listId") String listId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // Validate the JWT token
        // Extract the token from the authorization header
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);

        // Load user details from the token
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

        // Check if the token is valid
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            // Get the game list to be deleted
            GameList gameList = gameListService.getGameList(listId);

            // Check if the game list exists
            if (gameList == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Check if the token is valid
            if (!jwtUtils.validateToken(token, userDetails)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if (gameList.getGames() != null) {
                for (GameWithPlaytime game : gameList.getGames()) {
                    gameWithPlayTimeService.deleteGameById(game.getId().toString());
                }
            }
            // Delete the game list
            gameListService.deleteGameList(listId);

            // Return a success response with no content
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Helper method to extract the JWT token from the Authorization header.
     *
     * @param authorizationHeader The Authorization header value
     * @return The JWT token, or null if not found
     */
    private String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}
