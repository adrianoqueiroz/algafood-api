package com.algaworks.algafood.api.integration;

import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CozinhServiceIT {
    @LocalServerPort
    private int port;

    @Autowired
    CozinhaRepository cozinhaRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        prepararDados();
    }

    @Test
    public void deveRetornarOKQuandoConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConterCozinhasIndianaETailandesa() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("nome", hasItems("Indiana", "Tailandesa"));
    }

    @Test
    public void deveRtornarCreatedQuandoCadastrarCozinhas() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body("{ \"nome\": \"Italiana\" }")
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        Cozinha cozinha = Cozinha.builder()
            .nome("Americana")
            .build();

        Cozinha cozinhaSalva = cozinhaRepository.save(cozinha);

        given()
            .pathParam("cozinhaId", cozinhaSalva.getId())
            .accept(ContentType.JSON)
            .when()
            .get("/{cozinhaId}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Americana"));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        given()
            .pathParam("cozinhaId", 999999)
            .accept(ContentType.JSON)
            .when()
            .get("/{cozinhaId}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinha1 = Cozinha.builder()
            .nome("Indiana")
            .build();

        Cozinha cozinha2 = Cozinha.builder()
            .nome("Tailandesa")
            .build();

        cozinhaRepository.save(cozinha1);
        cozinhaRepository.save(cozinha2);
    }
}