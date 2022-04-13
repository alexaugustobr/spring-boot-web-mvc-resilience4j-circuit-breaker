package com.algaworks.example.mensagem.api;

import com.algaworks.example.mensagem.integration.IBGELocalidadesClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paises")
public class PaisController {
	
	private final IBGELocalidadesClient localidadesClient;

	public PaisController(IBGELocalidadesClient localidadesClient) {
		this.localidadesClient = localidadesClient;
	}

	@GetMapping
	public List<PaisResponse> buscarPaises(){
		return localidadesClient.getPaises();
	}
	
}
