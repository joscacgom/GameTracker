package co.empathy.academy.gametracker.models.elastic;

import co.empathy.academy.gametracker.models.Game;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "games")
public class ElasticGame extends Game {
}
