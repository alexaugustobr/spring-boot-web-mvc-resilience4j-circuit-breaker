package com.algaworks.example.resilience4j.produtos.infra.client;

import com.algaworks.example.resilience4j.produtos.client.avaliacoes.AvaliacaoClient;
import com.algaworks.example.resilience4j.produtos.client.avaliacoes.AvaliacaoModel;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.decorators.Decorators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class AvaliacaoClientImpl implements AvaliacaoClient {

	private final Logger logger = LoggerFactory.getLogger(AvaliacaoClientImpl.class);
	private final RestTemplate restTemplate;
	
	private final static String API_URL = UriComponentsBuilder
			.fromHttpUrl("http://localhost:8090/avaliacaos")
			.queryParam("produtoId", "{produtoId}")
			.encode()
			.toUriString();

	private final CircuitBreaker circuitBreaker;
	private final Map<Long, List<AvaliacaoModel>> CACHE = new HashMap<>();
	
	public AvaliacaoClientImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;

		CircuitBreakerConfig config = CircuitBreakerConfig.custom()
				.failureRateThreshold(50)
				.slidingWindowSize(10)
				.minimumNumberOfCalls(5)
				.permittedNumberOfCallsInHalfOpenState(5)
				.build();
		
		this.circuitBreaker = CircuitBreaker.of("avaliacaoCB", config);
		
		this.circuitBreaker.getEventPublisher()
				.onStateTransition(event -> {
					logger.info(event.toString().substring(53));
				});
	}

	@Override
	//@CircuitBreaker(fallbackMethod = "buscarTodosPorProdutoNoCache", name = "avaliacaoCB")
	public List<AvaliacaoModel> buscarTodosPorProduto(Long produtoId) {
		Supplier<List<AvaliacaoModel>> buscaAvaliacaoSupplier = Decorators
				.ofSupplier(() -> executarRequisicao(produtoId))
				.withCircuitBreaker(circuitBreaker)
				.withFallback(List.of(Exception.class), e -> this.buscarTodosPorProdutoNoCache(produtoId, e))
				.decorate();

		final List<AvaliacaoModel> avaliacoes = buscaAvaliacaoSupplier.get();

		return avaliacoes;
	}

	private List<AvaliacaoModel> executarRequisicao(Long produtoId) {
		final Map<String, Object> parametros = new HashMap<>();
		parametros.put("produtoId", produtoId);

		logger.info("Buscando avaliacoes");
		final var avaliacoes = restTemplate.getForObject(API_URL, AvaliacaoModel[].class, parametros);

		atualizarCache(produtoId, avaliacoes);

		return Arrays.asList(avaliacoes);
	}

	private void atualizarCache(Long produtoId, AvaliacaoModel[] avaliacoes) {
		logger.info("Atualizando cache");
		CACHE.put(produtoId, Arrays.asList(avaliacoes));
	}

	private List<AvaliacaoModel> buscarTodosPorProdutoNoCache(Long produtoId, Throwable e) {
		logger.info("Buscando avaliacoes no cache");
		return CACHE.getOrDefault(produtoId, new ArrayList<>());
	}
	
}
