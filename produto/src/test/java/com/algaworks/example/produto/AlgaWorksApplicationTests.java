package com.algaworks.example.produto;

import com.algaworks.example.produto.infra.client.AvaliacaoClientImpl;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.yml")
public class AlgaWorksApplicationTests {

	private static final long PRODUTO_ID_EXISTENTE = 1L;

	@LocalServerPort
	private int port;

	@SpyBean //Injeta o bean e faz Spy, para verificar quantas chamadas foram realizadas
	private AvaliacaoClientImpl avaliacaoClient;

	@Autowired //Repositório de CircuitBreakers
	private CircuitBreakerRegistry circuitBreakerRegistry;
	
	private CircuitBreaker avaliacaoCB;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/produtos";

		//Busca instância gerada no YML
		avaliacaoCB = circuitBreakerRegistry.circuitBreaker("avaliacaoCB");
	}

	@Test
	public void testCBOpen() {
		given()
				.pathParam("produtoId", PRODUTO_ID_EXISTENTE)
				.accept(ContentType.JSON)
			.when()
				.get("/{produtoId}")
			.then()
				.statusCode(HttpStatus.OK.value());

		//Chama busca normalmente
		Mockito.verify(avaliacaoClient, Mockito.times(1))
				.buscarTodosPorProduto(Mockito.anyLong());

		//Chama busca no cache
		Mockito.verify(avaliacaoClient, Mockito.times(1))
				.buscarNoCache(Mockito.anyLong(), Mockito.any());

		//Força abertura
		for (int i = 0; i < 10; i++) {
			given()
					.pathParam("produtoId", PRODUTO_ID_EXISTENTE)
					.accept(ContentType.JSON)
				.when()
					.get("/{produtoId}")
				.then()
					.statusCode(HttpStatus.OK.value());
		}

		Assertions.assertThat(avaliacaoCB.getState()).isEqualTo(CircuitBreaker.State.OPEN);
	}

}
