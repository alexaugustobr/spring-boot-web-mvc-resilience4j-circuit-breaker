package com.algaworks.example.produto.infra.client;

import com.algaworks.example.produto.client.avaliacoes.AvaliacaoClient;
import com.algaworks.example.produto.client.avaliacoes.AvaliacaoModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

@Component
public class AvaliacaoClientImpl implements AvaliacaoClient {

	private final Logger logger = LoggerFactory.getLogger(AvaliacaoClientImpl.class);
	private final RestTemplate restTemplate;

	private final static String API_URL = UriComponentsBuilder
			.fromHttpUrl("http://localhost:8090/avaliacaos")
			.queryParam("produtoId", "{produtoId}")
			.encode()
			.toUriString();

	private final Map<Long, List<AvaliacaoModel>> CACHE = new HashMap<>();

	public AvaliacaoClientImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	@CircuitBreaker(name = "avaliacaoCB", fallbackMethod = "buscarNoCache")
	public List<AvaliacaoModel> buscarTodosPorProduto(Long produtoId) {
		return executarRequisicao(produtoId);
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

	public List<AvaliacaoModel> buscarNoCache(Long produtoId, Throwable e) {
		logger.warn("Buscando avaliações no cache");
		return CACHE.getOrDefault(produtoId, new ArrayList<>());
	}

}
