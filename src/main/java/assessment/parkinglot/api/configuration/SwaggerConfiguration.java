package assessment.parkinglot.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("assessment.parkinglot.api")
                .build();
    }

    @Bean
    public OpenAPI springOpenAPI() {
        return (new OpenAPI())
                .info((new Info()).title("Parking Lot Service"));
    }
}
