package com.algaworks.example.resilience4j.produtos.infra.client;

import com.algaworks.example.resilience4j.produtos.client.avaliacoes.AvaliacaoClient;
import com.algaworks.example.resilience4j.produtos.client.avaliacoes.AvaliacaoModel;
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
	
	private final RestTemplate restTemplate;
	private final String avaliacaoApiUrl = "http://localhost:8090/avaliacaos";
	private final Logger logger = LoggerFactory.getLogger(AvaliacaoClientImpl.class);
	
	private final Map<Long, List<AvaliacaoModel>> CACHE = new HashMap<>();

	public AvaliacaoClientImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	@CircuitBreaker(fallbackMethod = "buscarTodosPorProdutoNoCache", name = "avaliacaoCB")
	public List<AvaliacaoModel> buscarTodosPorProduto(Long produtoId) {
		logger.info("Buscando avaliacoes");
		final Map<String, Object> parametros = new HashMap<>();
		parametros.put("produtoId", produtoId);

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(avaliacaoApiUrl)
				.queryParam("produtoId", "{produtoId}")
				.encode()
				.toUriString();

		final AvaliacaoModel[] avaliacaos = restTemplate.getForObject(urlTemplate,
				AvaliacaoModel[].class,
				parametros);

		if (avaliacaos == null) {
			return new ArrayList<>();
		}

		CACHE.put(produtoId, Arrays.asList(avaliacaos));

		return Arrays.asList(avaliacaos);
	}

	private List<AvaliacaoModel> buscarTodosPorProdutoNoCache(Long produtoId, Exception e) {
		logger.info("Buscando avaliacoes no cache");
		return CACHE.getOrDefault(produtoId, new ArrayList<>());
	}
	
}
