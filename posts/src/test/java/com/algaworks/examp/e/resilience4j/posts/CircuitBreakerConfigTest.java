package com.algaworks.examp.e.resilience4j.posts;

import com.algaworks.examp.e.resilience4j.posts.client.editors.EditorClient;
import com.algaworks.examp.e.resilience4j.posts.client.editors.EditorModel;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

public class CircuitBreakerConfigTest {
	
	@Test
	public void criandoUmCbExecutandoMetodo() {
		//Cria um CB com as configurações padrão
		CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("editors");

		//Criando uma implementação fictícia do EditorClient
		EditorClient editorTestClient = new EditorClient() {
			
			@Override
			public EditorModel getOne(Long id) {
				//Simula um erro interno no servidor
				if (id == null) {
					throw new RuntimeException();
				}
				//Simula uma falha de demora na resposta
				if (id.equals(4L)) {
					try {
						Thread.sleep(Duration.ofMinutes(1).toMillis());
					} catch (Exception e) {}
				}
				
				return new EditorModel(id, "Alex");
			}

			@Override
			public List<EditorModel> getAll() {
				return null;
			}
			
		};

		EditorModel editorModel = circuitBreaker.executeSupplier(() -> editorTestClient.getOne(1L));

		Assertions.assertThat(editorModel).isNotNull();
	}
	
}
