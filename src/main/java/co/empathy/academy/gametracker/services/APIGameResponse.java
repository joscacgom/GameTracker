package co.empathy.academy.gametracker.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import co.empathy.academy.gametracker.models.APIGame;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APIGameResponse {

    private List<APIGame> results;

    public List<APIGame> getResults() {
        return results;
    }

    public void setResults(List<APIGame> results) {
        this.results = results;
    }

}
