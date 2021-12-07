package pl.mkotra.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Profile("!integration-tests")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public RouterFunction<ServerResponse> redirect() {
        return route(
                GET("/"),
                req -> ServerResponse.temporaryRedirect(URI.create("swagger-ui/index.html")).build()
        );
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pl.mkotra.demo"))
                .paths(PathSelectors.any())
                .build();
    }
}