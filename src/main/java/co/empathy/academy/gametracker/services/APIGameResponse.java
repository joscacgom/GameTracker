package co.empathy.academy.gametracker.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import co.empathy.academy.gametracker.models.Game;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APIGameResponse {

    private List<Game> results;

    public List<Game> getResults() {
        return results;
    }

    public void setResults(List<Game> results) {
        this.results = results;
    }

}
