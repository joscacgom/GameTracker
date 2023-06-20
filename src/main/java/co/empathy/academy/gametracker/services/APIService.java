package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.APIGames;
import co.empathy.academy.gametracker.repositories.GameRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Service
public class APIService {

    @Autowired
    private GameRepository gameRepository;

    private OkHttpClient client = new OkHttpClient();

    public APIService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<APIGames> getAListOfGames() {
        // Llamada a RAWGAPI
        Request request = new Request.Builder()
                    .url("https://rawg-video-games-database.p.rapidapi.com/games?key=6ecc279ebc114b0194d9600c889c4ab9")
                    .get()
                    .addHeader("X-RapidAPI-Key", "2f671919fcmsh2c7423d14d84b80p1f2e01jsnfe69a30cb963")
                    .addHeader("X-RapidAPI-Host", "rawg-video-games-database.p.rapidapi.com")
                    .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Obtencion de la respuesta como cadena JSON
                String jsonResponse = response.body().string();

                if (jsonResponse.isBlank())
                    throw new IOException("Error response body is blank.");

                // Cadena JSON a Java
                ObjectMapper objectMapper = new ObjectMapper();
                //JsonNode rootNode = objectMapper.readTree(jsonResponse);
                //JsonNode resultsNode = rootNode.get("results");
                // resultsNode.toString()
                List<APIGames> games = objectMapper.readValue(jsonResponse, new TypeReference<List<APIGames>>() { });

                gameRepository.saveAll(games); // Guarda los juegos en la bbdd Mongo

                // return response.body().string();
                return games;
            }
            else
                throw new IOException("Error on API response.");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public String getGameDetails(String game_id) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://rawg-video-games-database.p.rapidapi.com/games/" + game_id)
                .get()
                .addHeader("X-RapidAPI-Key", "2f671919fcmsh2c7423d14d84b80p1f2e01jsnfe69a30cb963")
                .addHeader("X-RapidAPI-Host", "rawg-video-games-database.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful())
                return response.body().string();
            else
                throw new IOException("Error on API response.");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
