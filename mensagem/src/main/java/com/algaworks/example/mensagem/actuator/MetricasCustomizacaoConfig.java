package com.algaworks.example.mensagem.actuator;

import com.algaworks.example.mensagem.domain.MensagemRepository;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MetricasCustomizacaoConfig {

	@Bean
	public MeterBinder quantidadeMensagens(MensagemRepository mensagemRepository) {
		return (registry) -> Gauge.builder("mensagem.count", mensagemRepository::count).register(registry);
	}

	@Bean
	public CountedAspect countedAspect(MeterRegistry registry) {
		return new CountedAspect(registry);
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> tagsComuns(Environment environment) {
		return (registry) -> {
			final String profiles = String.join(",", environment.getActiveProfiles());
			registry.config().commonTags("profiles", profiles);
		};
	}
	
}