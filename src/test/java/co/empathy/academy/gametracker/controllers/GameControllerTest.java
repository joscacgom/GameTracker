package co.empathy.academy.gametracker.controllers;

import co.empathy.academy.gametracker.models.Game;
import co.empathy.academy.gametracker.services.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    private long gameId = 3498;
    private String gameName = "Grand Theft Auto V";

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    /**
     * TEST: Obtains a game with its information (details) from Mongo database
     */
    @Test
    public void getGameDetails() {
        Game game = new Game();
        game.setId(gameId);
        game.setName(gameName);
        when(gameService.getGame(gameId)).thenReturn(game);

        Game response = gameController.getGameDetails(gameId);
        assertEquals(gameId, response.getId());
        assertEquals(gameName, response.getName());
    }

    /**
     * TEST: Obtains a game with its information (details) from Mongo database NOT FOUND
     */
    @Test
    public void getGameDetailsNotFound() {
        long gameId = 24563;

        when(gameService.getGame(gameId)).thenReturn(null);

        Game response = gameController.getGameDetails(gameId);
        assertNull(response);
    }

}