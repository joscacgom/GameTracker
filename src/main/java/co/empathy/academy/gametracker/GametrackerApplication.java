package co.empathy.academy.gametracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableElasticsearchRepositories
public class GametrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GametrackerApplication.class, args);
	}

}
