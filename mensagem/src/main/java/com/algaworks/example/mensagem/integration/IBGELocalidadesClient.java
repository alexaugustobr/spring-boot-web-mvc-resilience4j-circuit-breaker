package com.algaworks.example.mensagem.integration;

import com.algaworks.example.mensagem.api.PaisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IBGELocalidadesClient {
	
	private final String paisAPIUrl;
	private final String localidadesAPIPaisPath;
	private final RestTemplate restTemplate;

	public IBGELocalidadesClient(@Value("${integracao.api.localidades.url}") String localidadesAPIUrl,
	                             @Value("${integracao.api.localidades.paises-path}") String localidadesAPIPaisPath,
	                             RestTemplate restTemplate) {
		this.paisAPIUrl = localidadesAPIUrl;
		this.localidadesAPIPaisPath = localidadesAPIPaisPath;
		this.restTemplate = restTemplate;
	}

	public List<PaisResponse> getPaises() {
		final var url = paisAPIUrl + localidadesAPIPaisPath;
		final var paisAPIResponse = restTemplate.getForObject(url, IBGEPaisResponse[].class);
		
		if (paisAPIResponse == null) {
			return new ArrayList<>();
		}
		
		return Arrays.stream(paisAPIResponse)
				.map(PaisResponse::deIBGEPais)
				.collect(Collectors.toList());
	}
	
}
