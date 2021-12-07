package pl.mkotra.demo.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.mkotra.demo.core.TransactionService;

import java.time.Duration;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.profiles.include=integration-tests"}
)
@AutoConfigureWebTestClient
abstract class BaseIT {

    static final String MONGO_DATABASE = "demo_integration_test";

    @Container
    static final MongoDBContainer mongo = new MongoDBContainer(DockerImageName.parse("mongo:4.4.8"));

    static {
        mongo.start();
    }

    @Autowired
    TransactionService transactionService;

    @Autowired
    protected WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        transactionService.deleteAll();
        webTestClient = webTestClient
                .mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();
    }

    @AfterEach
    public void tearDown() {
    }

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.database", () -> MONGO_DATABASE);
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }
}
