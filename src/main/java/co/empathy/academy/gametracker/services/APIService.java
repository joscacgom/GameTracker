package co.empathy.academy.gametracker.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class APIService {

    OkHttpClient client = new OkHttpClient();

    public String getAListOfGames() {
        Request request;
        request = new Request.Builder()
                    .url("https://rawg-video-games-database.p.rapidapi.com/games?key=6ecc279ebc114b0194d9600c889c4ab9")
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
