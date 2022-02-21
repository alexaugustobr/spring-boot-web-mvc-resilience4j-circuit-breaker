package com.algaworks.examp.e.resilience4j.posts.infra.client;

import com.algaworks.examp.e.resilience4j.posts.client.editors.EditorClient;
import com.algaworks.examp.e.resilience4j.posts.client.editors.EditorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class EditorClientImpl implements EditorClient {
	
	private final RestTemplate restTemplate;
	private final String editorApiUrl = "http://localhost:8090/editors";
	private final Logger logger = LoggerFactory.getLogger(EditorClientImpl.class);

	public EditorClientImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public EditorModel getOne(Long id) {
		try {
			logger.info("Buscando editor por id "  + id);
			return restTemplate.getForObject(editorApiUrl + "/" + id, EditorModel.class);
		} catch (Exception e) {
			logger.error("Erro ao buscar editor");
			throw e;
		}
	}

	@Override
	public List<EditorModel> getAll() {
		try {
			logger.info("Buscando editores");
			final EditorModel[] editors = restTemplate.getForObject(editorApiUrl, EditorModel[].class);
			if (editors == null) {
				return new ArrayList<>();
			}
			return Arrays.asList(editors);
		} catch (Exception e) {
			logger.error("Erro ao buscar editores");
			return new ArrayList<>();
		}
	}
}
