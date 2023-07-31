package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.ElasticGame;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticGameRepository extends ElasticsearchRepository<ElasticGame, Long> {

}