package com.algaworks.example.config;

import com.algaworks.example.mensagem.domain.Mensagem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.algaworks.example.mensagem",
		entityManagerFactoryRef = "mensagensEntityManagerFactory",
		transactionManagerRef = "mensagensTransactionManager"
)
public class MensagemJpaConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean mensagensEntityManagerFactory(
			@Qualifier("mensagensDataSource") DataSource mensagensDataSource, 
			EntityManagerFactoryBuilder builder) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		return builder.dataSource(mensagensDataSource)
				.properties(properties)
				.packages(Mensagem.class)
				.build();
	}

	@Bean
	public PlatformTransactionManager mensagensTransactionManager(
			@Qualifier("mensagensEntityManagerFactory") LocalContainerEntityManagerFactoryBean mensagensEMF) {
		return new JpaTransactionManager(Objects.requireNonNull(mensagensEMF.getObject()));
	}
	
}
