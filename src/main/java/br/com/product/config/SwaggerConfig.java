package br.com.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	@Bean
    public OpenAPI OpenAPI() {
    	
		return new OpenAPI()
                .components(new Components()
                .addSecuritySchemes("basicScheme",  new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info()
                .version("0.0.1")		
				.title(String.format("Documentation API"))
				.description( "Essa API foi desenvolvida para realizar cadastro, atualização, exclusão e consulta referente aos produtos.")
				.contact(new Contact()
						.name("Developer Daniel Cavalcante")
						.email("daniel16henrrique@gmail.com")));
	}
                
}
