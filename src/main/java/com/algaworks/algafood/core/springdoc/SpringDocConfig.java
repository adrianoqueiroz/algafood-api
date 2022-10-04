package com.algaworks.algafood.core.springdoc;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "security", type = SecuritySchemeType.OAUTH2,
    flows = @OAuthFlows(authorizationCode = @OAuthFlow(
        authorizationUrl = "${springdoc.security.oauth2.authorizationUrl}",
        tokenUrl = "${springdoc.security.oauth2.tokenUrl}",
        scopes = {
            @OAuthScope(name = "READ", description = "Acesso de leitura"),
            @OAuthScope(name = "WRITE", description = "Acesso de escrita")
        })))

public class SpringDocConfig {

    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("AlgaFood API")
                .version("1")
                .description("Rest API do AlgaFood")
                .contact(new Contact()
                    .name("Adriano Queiroz")
                    .email("adriano.nq@gmail.com")
                )
            )
            .externalDocs(new ExternalDocumentation()
                .description("AlgaFood")
                .url("https://www.algaworks.com")
            );
    }

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> {
            openApi.getPaths()
                .values()
                .stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    ApiResponses responses = operation.getResponses();

                    ApiResponse apiResponseNaoEncontrado = new ApiResponse().description("Recurso não encontrado");
                    ApiResponse apiResponseErroInterno = new ApiResponse().description("Erro interno no servidor");
                    ApiResponse apiResponseSemRepresentacao = new ApiResponse()
                        .description("Recurso não possui uma representação que poderia ser aceita pelo consumidor");

                    responses.addApiResponse("404", apiResponseNaoEncontrado);
                    responses.addApiResponse("406", apiResponseSemRepresentacao);
                    responses.addApiResponse("500", apiResponseErroInterno);
                });
        };
    }
}
