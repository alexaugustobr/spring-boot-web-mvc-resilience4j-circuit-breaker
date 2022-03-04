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

		final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
				.slidingWindowSize(8)
				.minimumNumberOfCalls(4)
				.build();

		this.circuitBreaker = CircuitBreaker.of("avaliacaoCB", config);
		
		this.circuitBreaker.getEventPublisher()
				.onStateTransition(event -> {
					logger.info(event.toString());
				});
	}

	@Override
	public List<AvaliacaoModel> buscarTodosPorProduto(Long produtoId) {
		Supplier<List<AvaliacaoModel>> buscaAvaliacaoSupplier = Decorators
				.ofSupplier(() -> executarRequisicao(produtoId))
				.withCircuitBreaker(circuitBreaker)
				.withFallback(List.of(Exception.class), e -> this.buscarTodosPorProdutoNoCache(produtoId))
				.decorate();

		final List<AvaliacaoModel> avaliacoes = buscaAvaliacaoSupplier.get();

		return avaliacoes;
	}

	private List<AvaliacaoModel> buscarTodosPorProdutoNoCache(Long produtoId) {
		logger.warn("Buscando avaliações no cache");
		return CACHE.getOrDefault(produtoId, new ArrayList<>());
	}

	private List<AvaliacaoModel> executarRequisicao(Long produtoId) {
		final Map<String, Object> parametros = new HashMap<>();
		parametros.put("produtoId", produtoId);

		logger.info("Buscando avaliações");
		final AvaliacaoModel[] avaliacoes;
		
		try {
			avaliacoes = restTemplate.getForObject(API_URL, AvaliacaoModel[].class, parametros);
		} catch (Exception e) {
			logger.error("Erro ao buscar avaliações");
			throw e;
		}
		
		CACHE.put(produtoId, Arrays.asList(avaliacoes));

		return Arrays.asList(avaliacoes);
	}
	
}
