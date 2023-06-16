package co.empathy.academy.gametracker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
import co.empathy.academy.gametracker.services.GameListService;
import co.empathy.academy.gametracker.services.UserService;
import co.empathy.academy.gametracker.utils.JWTUtils;

@RestController
@RequestMapping("/api/game-lists")
public class GameListController {

    private final GameListService gameListService;
    private final UserService userService;
    private final JWTUtils jwtUtils;

    public GameListController(GameListService gameListService, JWTUtils jwtUtils, UserService userService) {
        this.gameListService = gameListService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<GameList> createGameList(@RequestBody GameList gameList, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));
        if (token == null || !jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        GameList createdGameList = gameListService.createGameList(gameList);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGameList);
    }

    @PutMapping("/{listId}")
    public ResponseEntity<GameList> updateGameList(@PathVariable("listId") String listId, @RequestBody GameList gameList, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));
        if (token == null || !jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // maybe, add an endpoint to update the games in the list, and to pass one game from the current list to another

        GameList updatedGameList = gameListService.updateGameList(listId, gameList);
        if (updatedGameList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedGameList);
    }

    @GetMapping("/{listId}")
    public ResponseEntity<GameList> getGameList(@PathVariable("listId") String listId, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        GameList gameList = gameListService.getGameList(listId);

        // query games from the list (DTO)
        if (gameList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!jwtUtils.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(gameList);
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteGameList(@PathVariable("listId") String listId, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUsernameFromToken(token));

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            GameList gameList = gameListService.getGameList(listId);
            if (gameList == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (!jwtUtils.validateToken(token, userDetails)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            //Check that it only deletes the game list and its games associated, not the games of the db
            gameListService.deleteGameList(listId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
