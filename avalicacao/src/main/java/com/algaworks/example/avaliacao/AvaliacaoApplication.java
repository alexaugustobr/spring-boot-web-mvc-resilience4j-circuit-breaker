package com.algaworks.example.avaliacao;

import com.giffing.bucket4j.spring.boot.starter.config.cache.SyncCacheResolver;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class AvaliacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvaliacaoApplication.class, args);
	}

	@Bean
	public SyncCacheResolver rateLimitingBucketCacheResolver(HazelcastInstance hazelcastInstance) {
		return new HazelcastCacheResolver(hazelcastInstance);
	}

}
