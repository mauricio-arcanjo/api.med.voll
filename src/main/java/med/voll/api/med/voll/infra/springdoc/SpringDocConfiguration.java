package med.voll.api.med.voll.infra.springdoc;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//This class is to configure personalization of swagger to generate docs. Needs to permit access in SecurityConfigurations
@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()  // Extra information about the project
                        .title("Voll.med API")
                        .description("REST API for the Voll.med application, including CRUD functionalities for doctors and patients, as well as appointment scheduling and cancellation.")
                        .contact(new Contact()
                                .name("Backend Team")
                                .email("backend@voll.med"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://voll.med/api/license")));

    }

}

