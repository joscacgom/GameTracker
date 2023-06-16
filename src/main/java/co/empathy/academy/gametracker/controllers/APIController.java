package co.empathy.academy.gametracker.controllers;

import co.empathy.academy.gametracker.services.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private final APIService apiService;

    public APIController(APIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/listGames")
    public ResponseEntity<String> getAListOfGames() {
        String data = apiService.getAListOfGames();
        if (data != null)
            return ResponseEntity.ok(data); // Retorno: respuesta ok con data en el cuerpo.
        else // Retorno: respuesta de error con mensaje.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting a list of games from RAWG API.");
    }

    @GetMapping("/gameDetails/{game_id}")
    public ResponseEntity<String> getGameDetails(@PathVariable String game_id) {
        String data = apiService.getGameDetails(game_id);
        if (data != null)
            return ResponseEntity.ok(data); // Retorno: respuesta ok con data en el cuerpo.
        else // Retorno: respuesta de error con mensaje.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting game details from RAWG API.");
    }

}
