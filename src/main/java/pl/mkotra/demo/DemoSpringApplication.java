package pl.mkotra.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
public class DemoSpringApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DemoSpringApplication.class)
				.run(args);
	}
}
