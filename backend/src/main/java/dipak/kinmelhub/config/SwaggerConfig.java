package dipak.kinmelhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
@Bean
public OpenAPI customSwagger() {
	return new OpenAPI().info(new Info().title("Kinmelhub")
			.description("by Dipak Raj Pandey"));
}
}
