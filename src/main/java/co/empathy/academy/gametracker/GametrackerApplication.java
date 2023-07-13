package co.empathy.academy.gametracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "co.empathy.academy.gametracker.repositories",
		excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "co\\.empathy\\.academy\\.gametracker\\.repositories\\.elastic\\..*"))
public class GametrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GametrackerApplication.class, args);
	}

}
