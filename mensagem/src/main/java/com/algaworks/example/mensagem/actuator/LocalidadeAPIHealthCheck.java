package com.algaworks.example.mensagem.actuator;

import com.algaworks.example.mensagem.integration.LocalidadesClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("localidadeIntegracao")
public class LocalidadeAPIHealthCheck implements HealthIndicator {
	
	private final LocalidadesClient localidadesClient;

	public LocalidadeAPIHealthCheck(LocalidadesClient localidadesClient) {
		this.localidadesClient = localidadesClient;
	}

	@Override
	public Health health() {
		if (localidadesClient.estaOnline()) {
			return Health.up().withDetail("mensagem", "Comunicação ok.").build();
		}
		return Health.down().withDetail("mensagem", "Comunicação falhou.").build();
	}
}
